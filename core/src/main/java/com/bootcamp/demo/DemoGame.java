package com.bootcamp.demo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.Rarity;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.TacticalSaveData;
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

        loadSaveData();

        final TacticalSaveData tacticalsSaveData = new TacticalSaveData();
        tacticalsSaveData.setName("present");
        tacticalsSaveData.setLevel(3);
        tacticalsSaveData.setPower(750);
        tacticalsSaveData.setRarity(Rarity.EPIC);
        tacticalsSaveData.setStarCount(2);

        final MilitaryGearSaveData militaryGearSaveData = new MilitaryGearSaveData();
        militaryGearSaveData.setName("bloody-grail");
        militaryGearSaveData.setLevel(5);
        militaryGearSaveData.setPower(490);
        militaryGearSaveData.setRarity(Rarity.RARE);
        militaryGearSaveData.setStarCount(2);
        militaryGearSaveData.setRank("A");


        API.get(SaveData.class).getTacticalsSaveData().getTacticals().put(0, tacticalsSaveData);
        API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().put(0, militaryGearSaveData);
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

    @Override
    public void dispose () {
        super.dispose();
        API.Instance().dispose();
        Gdx.app.exit();
    }
}
