package com.bootcamp.demo.data.game;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.engine.Resources;
import lombok.Getter;

import java.util.Locale;

public class MilitaryGearGameData implements IGameData {
    @Getter
    private String name;
    @Getter
    private String title;
    @Getter
    private Drawable icon;
    @Getter
    private MilitaryGearSlot type;

    @Override
    public void load (XmlReader.Element rootXml) {
        name = rootXml.getAttribute("name");
        title = rootXml.getAttribute("title");
        icon = Resources.getDrawable(rootXml.getAttribute("icon"));
        type = MilitaryGearSlot.valueOf(rootXml.getAttribute("type").toUpperCase(Locale.ENGLISH));
    }
}
