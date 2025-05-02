package com.bootcamp.demo.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.*;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.FlagDialog;
import com.bootcamp.demo.dialogs.PetDialog;
import com.bootcamp.demo.dialogs.TacticalDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.managers.API;

public class DataGenerator {

    public static void generateTacticalData () {
        final ObjectMap<String, TacticalGameData> tacticalsMap = API.get(GameData.class).getTacticalsGameData().getTacticalsMap();
        final String randomTacticalName = Utils.getRandomKey(tacticalsMap);
        final TacticalSaveData tacticalSaveData = new TacticalSaveData();

        tacticalSaveData.setName(randomTacticalName);
        tacticalSaveData.setLevel(MathUtils.random(1, 10));
        tacticalSaveData.setRarity(Rarity.values[MathUtils.random(Rarity.values().length - 1)]);
        tacticalSaveData.setStarCount(MathUtils.random(1, 3));
        tacticalSaveData.setStatsData(generateStatsData());

        ObjectMap<String, TacticalSaveData> tacticalsSaveDataMap = API.get(SaveData.class).getTacticalsSaveData().getTacticals();
        if (!tacticalsSaveDataMap.containsKey(tacticalSaveData.getName())) {
            tacticalsSaveDataMap.put(tacticalSaveData.getName(), tacticalSaveData);
            API.get(DialogManager.class).getDialog(TacticalDialog.class).setData(API.get(SaveData.class).getTacticalsSaveData());
            API.get(SaveData.class).save();
        }
    }

    public static void generateFlagData () {
        final FlagSaveData flagSaveData = new FlagSaveData();
        ObjectMap<String, FlagGameData> flagsMap = API.get(GameData.class).getFlagsGameData().getFlagsMap();
        final String randomFlagName = Utils.getRandomKey(flagsMap);

        flagSaveData.setName(randomFlagName);
        flagSaveData.setLevel(MathUtils.random(1, 10));
        flagSaveData.setRarity(Rarity.values[MathUtils.random(Rarity.values().length - 1)]);
        flagSaveData.setStarCount(MathUtils.random(1, 3));
        flagSaveData.setStatsData(generateStatsData());

        ObjectMap<String, FlagSaveData> flagsSaveDataMap = API.get(SaveData.class).getFlagsSaveData().getFlags();
        if (!flagsSaveDataMap.containsKey(flagSaveData.getName())) {
            flagsSaveDataMap.put(flagSaveData.getName(), flagSaveData);
            API.get(DialogManager.class).getDialog(FlagDialog.class).setData(API.get(SaveData.class).getFlagsSaveData());
            API.get(SaveData.class).save();
        }
    }

    public static void generatePetData () {
        final ObjectMap<String, PetGameData> petsMap = API.get(GameData.class).getPetsGameData().getPetsMap();
        final String randomPetName = Utils.getRandomKey(petsMap);
        final PetSaveData petSaveData = new PetSaveData();

        petSaveData.setName(randomPetName);
        petSaveData.setLevel(MathUtils.random(1, 10));
        petSaveData.setRarity(Rarity.values[MathUtils.random(Rarity.values().length - 1)]);
        petSaveData.setStarCount(MathUtils.random(1, 3));
        petSaveData.setStatsData(generateStatsData());

        ObjectMap<String, PetSaveData> petsSaveData = API.get(SaveData.class).getPetsSaveData().getPets();
        if (!petsSaveData.containsKey(petSaveData.getName())) {
            petsSaveData.put(petSaveData.getName(), petSaveData);
            API.get(DialogManager.class).getDialog(PetDialog.class).setData(API.get(SaveData.class).getPetsSaveData());
            API.get(SaveData.class).save();
        }
    }

    public static MilitaryGearSaveData generateGearData () {
        final MilitaryGearSlot slot = MilitaryGearSlot.values[MathUtils.random(MilitaryGearSlot.values.length - 1)];
        final ObjectMap<MilitaryGearSlot, ObjectMap<String, MilitaryGearGameData>> militarySlotsWithGears = API.get(GameData.class).getMilitaryGearsGameData().getMilitarySlotsWithGears();
        final ObjectMap<String, MilitaryGearGameData> militaryGearsGameDataPerSlot = militarySlotsWithGears.get(slot);
        final String randomMilitaryGearName = Utils.getRandomKey(militaryGearsGameDataPerSlot);

        final MilitaryGearSaveData gearSaveData = new MilitaryGearSaveData();
        gearSaveData.setName(randomMilitaryGearName);
        gearSaveData.setXp(Math.round(MathUtils.random(1.0f, 100.0f) * 100f) / 100f);
        gearSaveData.setLevel(MissionsManager.calculateLevelFromXp(gearSaveData.getXp()));
        gearSaveData.setRarity(Rarity.values[MathUtils.random(Rarity.values().length - 1)]);
        gearSaveData.setStarCount(MathUtils.random(1, 3));
        gearSaveData.setStatsData(generateStatsData());
        gearSaveData.setRank("" + "ABCDE".charAt(new java.util.Random().nextInt(5)));
        gearSaveData.setSlot(slot);

        return gearSaveData;
    }

    public static StatsData generateStatsData () {
        final StatsData statsData = new StatsData();
        final int nonZeroStatCount = MathUtils.random(1, 9);

        for (int i = 0; i < nonZeroStatCount; i++) {
            final StatData statData = new StatData();
            statData.setStat(Stat.values[MathUtils.random(Stat.values.length - 1)]);
            statData.setValue(MathUtils.random(1, 9) * 10);
            statData.setType(StatType.values[MathUtils.random(StatType.values.length - 1)]);
            statsData.getStats().put(statData.getStat(), statData);
        }
        return statsData;
    }
}
