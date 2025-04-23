package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

public class PetsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, PetSaveData> pets = new ObjectMap<>();
    @Getter
    private String equippedPet = "cactus";

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, PetSaveData> entry : pets.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        pets.clear();

        for (JsonValue value : jsonData) {
            final String slotName = value.name;
            final PetSaveData petSaveData = json.readValue(PetSaveData.class, value);
            pets.put(slotName, petSaveData);
        }
    }
}
