package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.*;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;

import java.util.EnumMap;

public class MissionsManager {

    public static StatsData getGeneralStatsData () {
        final StatsData statsData = new StatsData();

        for (Stat stat : Stat.values) {
            final StatData statData = new StatData();
            statData.setValue(0);
            statData.setStat(stat);

            if (stat == Stat.HP || stat == Stat.ATK) {
                statData.setType(StatType.NUMBER);
            } else {
                statData.setType(StatType.PERCENT);
            }

            statsData.putStatData(stat, statData);
        }

        final EnumMap<Stat, Float> cumulativePercents = new EnumMap<>(Stat.class);
        cumulativePercents.put(Stat.HP, (float) 0);
        cumulativePercents.put(Stat.ATK, (float) 0);

        final ObjectMap<MilitaryGearSlot, MilitaryGearSaveData> militaryGears = API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears();
        for (MilitaryGearSaveData gearSaveData : militaryGears.values()) {
            addStats(statsData, gearSaveData.getStatsData(), cumulativePercents);
        }

        final ObjectMap<String, TacticalSaveData> tacticals = API.get(SaveData.class).getTacticalsSaveData().getTacticals();
        for (TacticalSaveData tacticalSaveData : tacticals.values()) {
            addStats(statsData, tacticalSaveData.getStatsData(), cumulativePercents);
        }

        final ObjectMap<String, PetSaveData> pets = API.get(SaveData.class).getPetsSaveData().getPets();
        for (PetSaveData petSaveData : pets.values()) {
            addStats(statsData, petSaveData.getStatsData(), cumulativePercents);
        }

        final ObjectMap<String, FlagSaveData> flags = API.get(SaveData.class).getFlagsSaveData().getFlags();
        for (FlagSaveData flagSaveData : flags.values()) {
            addStats(statsData, flagSaveData.getStatsData(), cumulativePercents);
        }

        final StatData hpStatData = statsData.getStatData(Stat.HP);
        hpStatData.setValue(applyCumulativePercentModifier(hpStatData.getValue(), cumulativePercents.get(Stat.HP)));
        final StatData atkStatData = statsData.getStatData(Stat.ATK);
        atkStatData.setValue(applyCumulativePercentModifier(atkStatData.getValue(), cumulativePercents.get(Stat.ATK)));

        return statsData;
    }

    private static void addStats (StatsData statsData, StatsData currentSaveData, EnumMap<Stat, Float> cumulativePercents) {
        for (Stat stat : Stat.values) {
            final StatData additiveStatData = currentSaveData.getStatData(stat);
            final StatData currentStatData = statsData.getStatData(stat);
            if (additiveStatData == null) {
                continue;
            }
            if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.NUMBER) {
                currentStatData.setValue(addAdditiveStats(currentStatData, additiveStatData));
            } else if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.PERCENT) {
                final float additivePercent = cumulativePercents.get(additiveStatData.getStat());
                final float currentPercent = additiveStatData.getValue();
                cumulativePercents.put(additiveStatData.getStat(), additivePercent + currentPercent);
            } else if (currentStatData.getType() == StatType.PERCENT && additiveStatData.getType() == StatType.PERCENT) {
                currentStatData.setValue(addPercentageStats(currentStatData, additiveStatData));
            }
        }
    }

    public static float addAdditiveStats (StatData currentStatData, StatData additiveStatData) {
        return currentStatData.getValue() + additiveStatData.getValue();
    }

    public static float addPercentageStats (StatData currentStatData, StatData percentageStatData) {
        return currentStatData.getValue() + percentageStatData.getValue();
    }

    public static float applyCumulativePercentModifier (float currentNumber, float percent) {
        return currentNumber * (percent / 100 + 1);
    }

    public static float calculatePowerFromStats (StatsData statsData) {
        final EnumMap<StatType, Float> statTypeCumulativeValues = new EnumMap<>(StatType.class);

        for (StatData statData : statsData.getStats().values()) {
            if (statData == null) {
                continue;
            }
            statTypeCumulativeValues.put(statData.getType(), statTypeCumulativeValues.getOrDefault(statData.getType(), 0f) + statData.getValue());
        }
        return statTypeCumulativeValues.getOrDefault(StatType.NUMBER, 0f) * (1 + statTypeCumulativeValues.getOrDefault(StatType.PERCENT, 0f) / 100);
    }

    public static float calculateCumulativePower () {
        return calculatePowerFromStats(getGeneralStatsData());
    }

    public static int calculateLevelFromXp (float xp) {
        return (int) (xp / 100 + 1);
    }

    public static int getCumulativeLevel () {
        int level = 0;
        for (ObjectMap.Entry<MilitaryGearSlot, MilitaryGearSaveData> militaryGear : API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears()) {
            level += calculateLevelFromXp(militaryGear.value.getXp());
        }
        return level;
    }

    public static void equipGear (MilitaryGearSaveData militaryGearSaveData) {
        MilitaryGearGameData gearGameData = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(militaryGearSaveData.getName());
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(gearGameData.getType(), militaryGearSaveData);
        API.get(EventModule.class).fireEvent(EquipGearEvent.class);
    }

    public static void equipFlag (FlagSaveData flagSaveData) {
        if (flagSaveData == null) {
            return;
        }
        API.get(SaveData.class).getFlagsSaveData().setEquippedFlag(flagSaveData.getName());
        API.get(SaveData.class).save();
        API.get(EventModule.class).fireEvent(EquipFlagEvent.class);
    }

    public static void equipPet (PetSaveData petSaveData) {
        if (petSaveData == null) {
            return;
        }
        API.get(SaveData.class).getPetsSaveData().setEquippedPetId(petSaveData.getName());
        API.get(SaveData.class).save();
        API.get(EventModule.class).fireEvent(EquipPetEvent.class);
    }

    public static void equipTactical (TacticalSaveData tacticalSaveData, int slot) {
        if (tacticalSaveData == null) {
            return;
        }
        TacticalsSaveData tacticalsSaveData = API.get(SaveData.class).getTacticalsSaveData();
        tacticalsSaveData.putEquippedTactical(slot, tacticalSaveData.getName());
        tacticalsSaveData.getTacticals().get(tacticalsSaveData.getEquippedTacticals().get(slot)).setSlot(slot);
        API.get(SaveData.class).save();
        API.get(EventModule.class).fireEvent(EquipTacticalEvent.class);
    }

    public static void updateStats () {
        API.get(EventModule.class).fireEvent(UpdateStatsEvent.class);
    }

    public static void dropItem () {
        // adds xp to the cumulative xp based on the dropped item's xp and level
    }
}
