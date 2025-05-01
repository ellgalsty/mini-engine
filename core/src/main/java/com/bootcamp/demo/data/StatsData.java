package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import lombok.Getter;

public class StatsData implements Json.Serializable {
    @Getter
    private final OrderedMap<Stat, StatData> stats = new OrderedMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<Stat, StatData> entry : stats.entries()) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        stats.clear();

        for (JsonValue value : jsonData) {
            final Stat stat = Stat.valueOf(value.name);
            final StatData statSaveData = json.readValue(StatData.class, value);
            stats.put(stat, statSaveData);
        }
    }

    public void putStatData (Stat stat, StatData data) {
        stats.put(stat, data);
    }

    public StatData getStatData (Stat stat) {
        return stats.get(stat);
    }
}
