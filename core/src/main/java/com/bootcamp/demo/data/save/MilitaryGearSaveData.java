package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.Rarity;
import com.bootcamp.demo.data.StatsData;
import lombok.Getter;
import lombok.Setter;

public class MilitaryGearSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private float power;
    @Getter @Setter
    private StatsData statsData = new StatsData();
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private String rank;
    @Getter @Setter
    private MilitaryGearSlot slot;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private float xp;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("p", power);
        json.writeValue("sd", statsData);
        json.writeValue("r", rarity);
        json.writeValue("s", starCount);
        json.writeValue("rk", rank);
        json.writeValue("sl", slot);
        json.writeValue("xp", xp);
        json.writeValue("l", level);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        power = jsonValue.getFloat("p");
        statsData.read(json, jsonValue.get("sd"));
        rarity = Rarity.valueOf(jsonValue.getString("r"));
        starCount = jsonValue.getInt("s");
        rank = jsonValue.getString("rk");
        slot = MilitaryGearSlot.valueOf(jsonValue.getString("sl"));
        xp = jsonValue.getFloat("xp");
        level = jsonValue.getInt("l");
    }
}
