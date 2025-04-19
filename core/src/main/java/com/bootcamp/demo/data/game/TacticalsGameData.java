package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

public class TacticalsGameData implements IGameData {

    private ObjectMap<String, TacticalsGameData> militaryGearsSlotMap;

    // tacticals list
    @Override
    public void load (XmlReader.Element rootXml) {
        final Array<XmlReader.Element> tacticalsXml = rootXml.getChildrenByName("tactical");
        for(final XmlReader.Element tacticalXml : tacticalsXml) {

        }
    }
}
