package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

public class MilitaryGearsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<MilitaryGearSaveData> militaryGears = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<MilitaryGearSaveData> entry : militaryGears.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        militaryGears.clear();

        for (JsonValue value : jsonData) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final MilitaryGearSaveData militarySaveData = json.readValue(MilitaryGearSaveData.class, value);
            militaryGears.put(slotIndex, militarySaveData);
        }

    }
}
