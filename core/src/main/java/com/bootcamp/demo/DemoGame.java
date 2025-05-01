package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.*;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.events.GameStartedEvent;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.events.core.EventModule;

public class DemoGame extends Game {

    @Override
    public void create () {
        Gdx.input.setInputProcessor(new InputMultiplexer());

        final GameData gameData = new GameData();
        API.Instance().register(GameData.class, gameData);
        gameData.load();

        API.Instance().register(StatsData.class);

        loadSaveData();

        final StatsData statsData1 = generateStatsData();

        final TacticalSaveData tacticalsSaveData = new TacticalSaveData();
        tacticalsSaveData.setName("present");
        tacticalsSaveData.setLevel(3);
        tacticalsSaveData.setRarity(Rarity.ASCENDANT);
        tacticalsSaveData.setStarCount(2);
        tacticalsSaveData.setStatsData(statsData1);

        final StatsData statsData2 = generateStatsData();

        final MilitaryGearSaveData militaryGearSaveData1 = new MilitaryGearSaveData();
        militaryGearSaveData1.setName("bloody-grail");
        militaryGearSaveData1.setLevel(5);
        militaryGearSaveData1.setRarity(Rarity.ETHEREAL);
        militaryGearSaveData1.setStarCount(2);
        militaryGearSaveData1.setRank("A");
        militaryGearSaveData1.setStatsData(statsData2);

        final StatsData statsData3 = generateStatsData();

        final MilitaryGearSaveData militaryGearSaveData2 = new MilitaryGearSaveData();
        militaryGearSaveData2.setName("hard-armor");
        militaryGearSaveData2.setLevel(3);
        militaryGearSaveData2.setRarity(Rarity.DOMINION);
        militaryGearSaveData2.setStarCount(1);
        militaryGearSaveData2.setRank("C");
        militaryGearSaveData2.setStatsData(statsData3);

        final StatsData statsData4 = generateStatsData();

        final PetSaveData petSaveData = new PetSaveData();
        petSaveData.setName("cactus");
        petSaveData.setLevel(5);
        petSaveData.setRarity(Rarity.NUCLEAR);
        petSaveData.setStarCount(2);
        petSaveData.setStatsData(statsData4);

        final StatsData statsData5 = new StatsData();
        final StatData statData11 = new StatData();
        statData11.setStat(Stat.ATK);
        statData11.setValue(5);
        statData11.setType(StatType.PERCENT);

        final StatData statData22 = new StatData();
        statData22.setStat(Stat.HP);
        statData22.setValue(10);
        statData22.setType(StatType.PERCENT);

        statsData5.putStatData(statData11.getStat(), statData11);
        statsData5.putStatData(statData22.getStat(), statData22);

        final PetSaveData petSaveData2 = new PetSaveData();
        petSaveData2.setName("susu");
        petSaveData2.setLevel(7);
        petSaveData2.setRarity(Rarity.ELITE);
        petSaveData2.setStarCount(1);
        petSaveData2.setStatsData(statsData5);

        final StatsData statsData = new StatsData();
        final StatData statData1 = new StatData();
        statData1.setStat(Stat.ATK);
        statData1.setValue(5);
        statData1.setType(StatType.PERCENT);

        final StatData statData2 = new StatData();
        statData2.setStat(Stat.HP);
        statData2.setValue(10);
        statData2.setType(StatType.PERCENT);

        statsData.getStats().put(statData1.getStat(), statData1);
        statsData.getStats().put(statData2.getStat(), statData2);

        final FlagSaveData flagSaveData = new FlagSaveData();
        flagSaveData.setName("flash-flag");
        flagSaveData.setLevel(3);
        flagSaveData.setRarity(Rarity.IMMORTAL);
        flagSaveData.setStarCount(1);
        flagSaveData.setStatsData(statsData);

        API.get(SaveData.class).getTacticalsSaveData().getTacticals().put(tacticalsSaveData.getName(), tacticalsSaveData);
        MilitaryGearSlot gearSlot1 = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(militaryGearSaveData1.getName()).getType();
        MilitaryGearSlot gearSlot2 = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(militaryGearSaveData2.getName()).getType();
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(gearSlot2, militaryGearSaveData2);
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(gearSlot1, militaryGearSaveData1);
        API.get(SaveData.class).getPetsSaveData().getPets().put(petSaveData.getName(), petSaveData);
        API.get(SaveData.class).getPetsSaveData().getPets().put(petSaveData2.getName(), petSaveData2);
        API.get(SaveData.class).getFlagsSaveData().getFlags().put(flagSaveData.getName(), flagSaveData);
        savePlayerData();

        setScreen(new GameScreen());
        API.Instance().register(DemoGame.class, this);
        API.get(EventModule.class).fireEvent(GameStartedEvent.class);
    }

    private void loadSaveData () {
        final FileHandle file = getPlayerDataFileHandler();
        if (file.exists()) {
            createNewSaveData();
            return;
        }

        final JsonReader jsonReader = new JsonReader();
        final Json json = new Json();
        json.setIgnoreUnknownFields(true);

        final String dataString = file.readString();
        final JsonValue jsonValue = jsonReader.parse(dataString);
        final SaveData saveData = json.readValue(SaveData.class, jsonValue);
        API.Instance().register(SaveData.class, saveData);
    }

    private void createNewSaveData () {
        final SaveData saveData = new SaveData();
        API.Instance().register(SaveData.class, saveData);
        savePlayerData();
    }

    public void savePlayerData () {
        final SaveData saveData = API.get(SaveData.class);

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setIgnoreUnknownFields(true);

        final String dataToPersist = json.toJson(saveData);
        getPlayerDataFileHandler().writeString(dataToPersist, false);
    }

    private FileHandle getPlayerDataFileHandler () {
        final FileHandle playerDataFile = Gdx.files.local("usercache").child("player-data");
        // check if file exists; if not, create an empty file
        if (!playerDataFile.exists()) {
            playerDataFile.writeString("", false);
        }
        return playerDataFile;
    }

    private static StatsData generateStatsData () {
        final StatsData statsData = new StatsData();
        final StatData statData1 = new StatData();
        statData1.setStat(Stat.ATK);
        statData1.setValue(23);
        statData1.setType(StatType.NUMBER);

        final StatData statData2 = new StatData();
        statData2.setStat(Stat.HP);
        statData2.setValue(15);
        statData2.setType(StatType.NUMBER);

        statsData.getStats().put(statData1.getStat(), statData1);
        statsData.getStats().put(statData2.getStat(), statData2);

        return statsData;
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
