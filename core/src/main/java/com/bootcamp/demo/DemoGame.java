package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.StatData;
import com.bootcamp.demo.data.StatType;
import com.bootcamp.demo.data.StatsData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.Rarity;
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
        tacticalsSaveData.setPower(750);
        tacticalsSaveData.setRarity(Rarity.EPIC);
        tacticalsSaveData.setStarCount(2);
        tacticalsSaveData.setEquipped(true);
        tacticalsSaveData.setStatsData(statsData1);

        final StatsData statsData2 = generateStatsData();

        final MilitaryGearSaveData militaryGearSaveData1 = new MilitaryGearSaveData();
        militaryGearSaveData1.setName("bloody-grail");
        militaryGearSaveData1.setLevel(5);
        militaryGearSaveData1.setPower(490);
        militaryGearSaveData1.setRarity(Rarity.RARE);
        militaryGearSaveData1.setStarCount(2);
        militaryGearSaveData1.setRank("A");
        militaryGearSaveData1.setEquipped(true);
        militaryGearSaveData1.setStatsData(statsData2);

        final StatsData statsData3 = generateStatsData();

        final MilitaryGearSaveData militaryGearSaveData2 = new MilitaryGearSaveData();
        militaryGearSaveData2.setName("hard-armor");
        militaryGearSaveData2.setLevel(3);
        militaryGearSaveData2.setPower(500);
        militaryGearSaveData2.setRarity(Rarity.RARE);
        militaryGearSaveData2.setStarCount(1);
        militaryGearSaveData2.setRank("C");
        militaryGearSaveData2.setEquipped(true);
        militaryGearSaveData2.setStatsData(statsData3);

        final StatsData statsData4 = generateStatsData();

        final PetSaveData petSaveData = new PetSaveData();
        petSaveData.setName("cactus");
        petSaveData.setLevel(5);
        petSaveData.setRarity(Rarity.RARE);
        petSaveData.setStarCount(2);
        petSaveData.setEquipped(true);
        petSaveData.setStatsData(statsData4);

        final StatsData statsData5 = generateStatsData();

        final FlagSaveData flagSaveData = new FlagSaveData();
        flagSaveData.setName("flash-flag");
        flagSaveData.setLevel(3);
        flagSaveData.setRarity(Rarity.RARE);
        flagSaveData.setStarCount(1);
        flagSaveData.setEquipped(true);
        flagSaveData.setStatsData(statsData5);

        API.get(SaveData.class).getTacticalsSaveData().getTacticals().put(0, tacticalsSaveData);
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(0, militaryGearSaveData1);
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(1, militaryGearSaveData2);
        API.get(SaveData.class).getPetsSaveData().getPets().put(0, petSaveData);
        API.get(SaveData.class).getFlagsSaveData().getFlags().put(0, flagSaveData);
        savePlayerData();

        setScreen(new GameScreen());
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
        statData1.setStatNumber(23);
        statData1.setType(StatType.PERCENT);

        final StatData statData2 = new StatData();
        statData2.setStat(Stat.HP);
        statData2.setStatNumber(15);
        statData2.setType(StatType.NUMBER);

        final StatData statData3 = new StatData();
        statData3.setStat(Stat.ATK);
        statData3.setStatNumber(20);
        statData3.setType(StatType.PERCENT);

        statsData.getStats().put(0, statData1);
        statsData.getStats().put(1, statData2);
        statsData.getStats().put(2, statData3);

        return statsData;
    }

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
