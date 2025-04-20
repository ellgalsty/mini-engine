package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import lombok.Getter;
import lombok.Setter;

public class FlagSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private boolean equipped;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("r", rarity);
        json.writeValue("s", starCount);
        json.writeValue("e", equipped);
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        name = jsonData.getString("n");
        level = jsonData.getInt("l");
        rarity = Rarity.valueOf(jsonData.getString("r"));
        starCount = jsonData.getInt("s");
        equipped = jsonData.getBoolean("e");
    }
}
