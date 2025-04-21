package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.StatsData;
import lombok.Getter;
import lombok.Setter;

public class MilitaryGearSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private int power;
    @Getter @Setter
    private StatsData statsData = new StatsData();
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private String rank;
    @Getter @Setter
    private boolean equipped;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("sd", statsData);
        json.writeValue("r", rarity);
        json.writeValue("s", starCount);
        json.writeValue("rk", rank);
        json.writeValue("e", equipped);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        statsData.read(json, jsonValue.get("sd"));
        rarity = Rarity.valueOf(jsonValue.getString("r"));
        starCount = jsonValue.getInt("s");
        rank = jsonValue.getString("rk");
        equipped = jsonValue.getBoolean("e");
    }
}
