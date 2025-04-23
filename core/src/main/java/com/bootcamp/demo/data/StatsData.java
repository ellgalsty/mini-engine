package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

public class StatsData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, StatData> stats = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, StatData> entry : stats.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        stats.clear();

        for (JsonValue value : jsonData) {
            final String statName = String.valueOf(value.name);
            final StatData statSaveData = json.readValue(StatData.class, value);
            stats.put(statName, statSaveData);
        }
    }

    public void putStatData (Stat stat, StatData data) {
        stats.put(stat.name(), data);
    }

    public StatData getStatData (String statName) {
        return stats.get(statName);
    }
}
