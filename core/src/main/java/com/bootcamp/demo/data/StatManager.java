package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.managers.API;

import java.util.EnumMap;

public class StatManager {
    public static StatsData getGeneralStatsData () {
        StatsData statsData = new StatsData();

        for (Stat stat : Stat.values) {
            StatData statData = new StatData();
            statData.setStatNumber(0);
            statData.setStat(stat);

            if (stat == Stat.HP || stat == Stat.ATK) {
                statData.setType(StatType.NUMBER);
            } else {
                statData.setType(StatType.PERCENT);
            }

            statsData.putStatData(stat, statData);
        }

        EnumMap<Stat, Float> cumulativePercents = new EnumMap<>(Stat.class);
        cumulativePercents.put(Stat.HP, (float) 0);
        cumulativePercents.put(Stat.ATK, (float) 0);

        ObjectMap<MilitaryGearSlot, MilitaryGearSaveData> militaryGears = API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears();
        for (MilitaryGearSaveData gearSaveData : militaryGears.values()) {
            addStats(statsData, gearSaveData.getStatsData(), cumulativePercents);
        }

        ObjectMap<String, TacticalSaveData> tacticals = API.get(SaveData.class).getTacticalsSaveData().getTacticals();
        for (TacticalSaveData tacticalSaveData : tacticals.values()) {
            addStats(statsData, tacticalSaveData.getStatsData(), cumulativePercents);
        }

        ObjectMap<String, PetSaveData> pets = API.get(SaveData.class).getPetsSaveData().getPets();
        for (PetSaveData petSaveData : pets.values()) {
            addStats(statsData, petSaveData.getStatsData(), cumulativePercents);
        }

        ObjectMap<String, FlagSaveData> flags = API.get(SaveData.class).getFlagsSaveData().getFlags();
        for (FlagSaveData flagSaveData : flags.values()) {
            addStats(statsData, flagSaveData.getStatsData(), cumulativePercents);
        }

        StatData hpStatData = statsData.getStatData(Stat.HP);
        hpStatData.setStatNumber(applyCumulativePercentModifier(hpStatData.getStatNumber(), cumulativePercents.get(Stat.HP)));
        StatData atkStatData = statsData.getStatData(Stat.ATK);
        atkStatData.setStatNumber(applyCumulativePercentModifier(atkStatData.getStatNumber(), cumulativePercents.get(Stat.ATK)));

        return statsData;
    }

    private static void addStats (StatsData statsData, StatsData currentSaveData, EnumMap<Stat, Float> cumulativePercents) {
        for (Stat stat : Stat.values) {
            StatData additiveStatData = currentSaveData.getStatData(stat);
            StatData currentStatData = statsData.getStatData(stat);
            if (additiveStatData == null) {
                continue;
            }
            if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.NUMBER) {
                currentStatData.setStatNumber(addAdditiveStats(currentStatData, additiveStatData));
            } else if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.PERCENT) {
                float additivePercent = cumulativePercents.get(additiveStatData.getStat());
                float currentPercent = additiveStatData.getStatNumber();
                cumulativePercents.put(additiveStatData.getStat(), additivePercent + currentPercent);
            } else if (currentStatData.getType() == StatType.PERCENT && additiveStatData.getType() == StatType.PERCENT) {
                currentStatData.setStatNumber(addPercentageStats(currentStatData, additiveStatData));
            }
        }
    }

    public static float addAdditiveStats (StatData currentStatData, StatData additiveStatData) {
        return currentStatData.getStatNumber() + additiveStatData.getStatNumber();
    }

    public static float addPercentageStats (StatData currentStatData, StatData percentageStatData) {
        return currentStatData.getStatNumber() + percentageStatData.getStatNumber();
    }

    public static float applyCumulativePercentModifier (float currentNumber, float percent) {
        return currentNumber * (percent / 100 + 1);
    }

    public static float calculatePowerFromStats (StatsData statsData) {
        EnumMap<StatType, Float> statTypeCumulativeValues = new EnumMap<>(StatType.class);

        for (StatData statData: statsData.getStats().values()) {
            if (statData == null) {
                continue;
            }
            statTypeCumulativeValues.put(statData.getType(), statTypeCumulativeValues.getOrDefault(statData.getType(), 0f)+statData.getStatNumber());
        }
        return statTypeCumulativeValues.get(StatType.NUMBER) * (1 + statTypeCumulativeValues.getOrDefault(StatType.PERCENT, 0f)/100);
    }

    public static float calculateCumulativePower () {
        return calculatePowerFromStats(getGeneralStatsData());
    }
}
