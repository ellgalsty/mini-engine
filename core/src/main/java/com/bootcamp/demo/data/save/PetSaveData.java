package com.bootcamp.demo.data.save;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.bootcamp.demo.data.Rarity;
import lombok.Getter;
import lombok.Setter;

public class PetSaveData implements Json.Serializable {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int level;
    @Getter @Setter
    private int starCount;
    @Getter @Setter
    private Rarity rarity;
    @Getter @Setter
    private boolean equipped;

    @Override
    public void write (Json json) {
        json.writeValue("n", name);
        json.writeValue("l", level);
        json.writeValue("s", starCount);
        json.writeValue("r", rarity);
        json.writeValue("e", equipped);
    }

    @Override
    public void read (Json json, JsonValue jsonData) {
        name = jsonData.getString("n");
        level = jsonData.getInt("l");
        starCount = jsonData.getInt("s");
        rarity = Rarity.valueOf(jsonData.getString("r"));
        equipped = jsonData.getBoolean("e");
    }
}
