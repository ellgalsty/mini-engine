package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

public class TacticalsSaveData implements Json.Serializable {
    @Getter
    private final IntMap<TacticalSaveData> tacticals = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<TacticalSaveData> entry : tacticals.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        tacticals.clear();

        for (JsonValue value : jsonValue) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final TacticalSaveData tacticalSaveData = json.readValue(TacticalSaveData.class, value);
            tacticals.put(slotIndex, tacticalSaveData);
        }
    }
}
