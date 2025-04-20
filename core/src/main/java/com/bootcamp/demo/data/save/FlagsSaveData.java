package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

public class FlagsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<FlagSaveData> flags = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<FlagSaveData> entry : flags.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        flags.clear();

        for (JsonValue value : jsonData) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final FlagSaveData flagSaveData = json.readValue(FlagSaveData.class, value);
            flags.put(slotIndex, flagSaveData);
        }
    }
}
