package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.game.Rarity;
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
    private Rarity rarity;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private String rank;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("p", power);
        json.writeValue("r", rarity);
        json.writeValue("s", starCount);
        json.writeValue("ra", rank);
    }

    @Override
    public void read (Json json, JsonValue jsonValue) {
        name = jsonValue.getString("n");
        level = jsonValue.getInt("l");
        power = jsonValue.getInt("p");
        rarity = Rarity.valueOf(jsonValue.getString("r"));
        starCount = jsonValue.getInt("s");
        rank = jsonValue.getString("ra");
    }
}
