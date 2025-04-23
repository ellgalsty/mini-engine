package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.managers.API;

public class DataUtils {
    public static StatsData getGeneralStatsData () {
        StatsData statsData = new StatsData();
        for (Stat stat: Stat.values) {
            StatData statData = new StatData();
            statData.setStatNumber(0);
            statData.setStat(stat);
            if (stat == Stat.HP || stat == Stat.ATK){
                statData.setType(StatType.NUMBER);
            }
            else {
                statData.setType(StatType.PERCENT);
            }
            statsData.putStatData(stat, statData);
        }
        ObjectMap<String, MilitaryGearSaveData> militaryGears = API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears();
        for (MilitaryGearSaveData gearSaveData : militaryGears.values()) {
            addStats(statsData, gearSaveData.getStatsData());
        }
        ObjectMap<String, TacticalSaveData> tacticals = API.get(SaveData.class).getTacticalsSaveData().getTacticals();
        for (TacticalSaveData tacticalSaveData : tacticals.values()) {
            addStats(statsData, tacticalSaveData.getStatsData());
        }
        ObjectMap<String, PetSaveData> pets = API.get(SaveData.class).getPetsSaveData().getPets();
        for (PetSaveData petSaveData : pets.values()) {
            addStats(statsData, petSaveData.getStatsData());
        }
        ObjectMap<String, FlagSaveData> flags = API.get(SaveData.class).getFlagsSaveData().getFlags();
        for (FlagSaveData flagSaveData : flags.values()) {
            addStats(statsData, flagSaveData.getStatsData());
        }

        return statsData;
    }

    private static void addStats (StatsData statsData, StatsData currentSaveData) {
        for (Stat stat : Stat.values) {
            StatData additiveStatData = currentSaveData.getStatData(stat.name());
            StatData currentStatData = statsData.getStatData(stat.name());
            if (additiveStatData == null) {
                continue;
            }
            if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.NUMBER) {
                currentStatData.setStatNumber(addAdditiveStats(currentStatData, additiveStatData));
            } else if (currentStatData.getType() == StatType.NUMBER && additiveStatData.getType() == StatType.PERCENT) {
                // add the logic of percentage count
            } else if (currentStatData.getType() == StatType.PERCENT && additiveStatData.getType() == StatType.PERCENT) {
                currentStatData.setStatNumber(addPercentage(currentStatData, additiveStatData));
            }
        }
    }

    public static float addAdditiveStats (StatData currentStatData, StatData additiveStatData) {
        return currentStatData.getStatNumber()  + additiveStatData.getStatNumber();
    }

    public static float addPercentage (StatData currentStatData, StatData percentageStatData) {
        return currentStatData.getStatNumber() + percentageStatData.getStatNumber();
    }

    public static float addPercentageToCurrent (StatData currentStatData, StatData percentageStatData) {
        return currentStatData.getStatNumber() + (percentageStatData.getStatNumber()*currentStatData.getStatNumber())/100;
    }
}
