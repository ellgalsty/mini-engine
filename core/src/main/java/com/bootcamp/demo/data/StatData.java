package com.bootcamp.demo.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

public class StatData implements Json.Serializable {
    @Getter @Setter
    private Stat stat;
    @Getter @Setter
    private float value;
    @Getter @Setter
    private StatType type;

    @Override
    public void write (Json json) {
        json.writeValue("s", stat);
        json.writeValue("v", value);
        json.writeValue("t", type);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        stat = Stat.valueOf(jsonValue.getString("s"));
        value = jsonValue.getFloat("v");
        type = StatType.valueOf(jsonValue.getString("t").toUpperCase(Locale.ENGLISH));
    }
}
