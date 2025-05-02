package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.bootcamp.demo.DemoGame;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class SaveData {

    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();
    @Getter
    private final MilitaryGearsSaveData militaryGearsSaveData = new MilitaryGearsSaveData();
    @Getter
    private final PetsSaveData petsSaveData = new PetsSaveData();
    @Getter
    private final FlagsSaveData flagsSaveData = new FlagsSaveData();

    public void save () {
        final SaveData saveData = API.get(SaveData.class);

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setIgnoreUnknownFields(true);

        final String dataToPersist = json.toJson(saveData);
        API.get(DemoGame.class).getPlayerDataFileHandler().writeString(dataToPersist, false);
    }
}
