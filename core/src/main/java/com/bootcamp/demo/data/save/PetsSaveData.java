package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.PetsGameData;
import lombok.Getter;

public class PetsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<PetSaveData> pets = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<PetSaveData> entry : pets.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        pets.clear();

        for (JsonValue value : jsonData) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final PetSaveData petSaveData = json.readValue(PetSaveData.class, value);
            pets.put(slotIndex, petSaveData);
        }
    }
}
