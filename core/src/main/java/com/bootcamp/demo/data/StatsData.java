package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;

public class StatsData implements Json.Serializable {
    @Getter
    private final IntMap<StatData> stats = new IntMap<>();

    @Override
    public void write (Json json) {
        for (IntMap.Entry<StatData> entry : stats.entries()) {
            json.writeValue(String.valueOf(entry.key), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        stats.clear();

        for (JsonValue value : jsonData) {
            final Integer slotIndex = Integer.valueOf(value.name);
            final StatData statSaveData = json.readValue(StatData.class, value);
            stats.put(slotIndex, statSaveData);
        }
    }
}
