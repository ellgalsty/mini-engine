package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.MilitaryGearSlot;
import lombok.Getter;

public class MilitaryGearsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, MilitaryGearSaveData> militaryGears = new ObjectMap<>();
    @Getter
    private ObjectMap<MilitaryGearSlot, String> equippedGears = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, MilitaryGearSaveData> entry : militaryGears) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        militaryGears.clear();

        for (JsonValue value : jsonData) {
            final String slotName = value.name;
            final MilitaryGearSaveData militarySaveData = json.readValue(MilitaryGearSaveData.class, value);
            militaryGears.put(slotName, militarySaveData);
        }

    }
}
