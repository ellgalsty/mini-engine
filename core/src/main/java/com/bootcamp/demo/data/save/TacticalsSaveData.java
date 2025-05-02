package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.*;
import lombok.Getter;

public class TacticalsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<String, TacticalSaveData> tacticals = new ObjectMap<>();
    @Getter
    private final OrderedMap<Integer, String> equippedTacticals = new OrderedMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<String, TacticalSaveData> entry : tacticals.entries()) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        tacticals.clear();

        for (JsonValue value : jsonValue) {
            final String slotName = value.name;
            final TacticalSaveData tacticalSaveData = json.readValue(TacticalSaveData.class, value);
            tacticals.put(slotName, tacticalSaveData);
        }
    }

    public void putEquippedTactical (int slotNumber, String equippedTactical) {
        equippedTacticals.put(slotNumber, equippedTactical);
    }
}
