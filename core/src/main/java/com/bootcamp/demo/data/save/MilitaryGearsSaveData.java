package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.MilitaryGearSlot;
import lombok.Getter;

import java.util.Locale;

public class MilitaryGearsSaveData implements Json.Serializable {
    @Getter
    private final ObjectMap<MilitaryGearSlot, MilitaryGearSaveData> militaryGears = new ObjectMap<>();

    @Override
    public void write (Json json) {
        for (ObjectMap.Entry<MilitaryGearSlot, MilitaryGearSaveData> entry : militaryGears) {
            json.writeValue(entry.key.name(), entry.value);
        }
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        militaryGears.clear();

        for (JsonValue value : jsonData) {
            final MilitaryGearSlot slot = MilitaryGearSlot.valueOf(value.name.toUpperCase(Locale.ENGLISH));
            final MilitaryGearSaveData militarySaveData = json.readValue(MilitaryGearSaveData.class, value);
            militaryGears.put(slot, militarySaveData);
        }

    }
}
