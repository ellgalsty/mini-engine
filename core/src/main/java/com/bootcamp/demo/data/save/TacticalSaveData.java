package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.StatsData;
import lombok.Getter;
import lombok.Setter;

public class TacticalSaveData implements Json.Serializable {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private StatsData statsData = new StatsData();
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private int slot;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("sd", statsData);
        json.writeValue("r", rarity);
        json.writeValue("sc", starCount);
        json.writeValue("s", slot);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        statsData.read(json, jsonValue.get("sd"));
        rarity = Rarity.valueOf(jsonValue.getString("r"));
        starCount = jsonValue.getInt("sc");
        slot = jsonValue.getInt("s");
    }
}
