package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class TacticalsGameData implements IGameData {

    @Getter
    private final ObjectMap<String, TacticalGameData> tacticalsMap = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        tacticalsMap.clear();
        final Array<XmlReader.Element> tacticalsXml = rootXml.getChildrenByName("tactical");
        for (XmlReader.Element tacticalXml : tacticalsXml) {
            final TacticalGameData tacticalGameData = new TacticalGameData();
            tacticalGameData.load(tacticalXml);
            tacticalsMap.put(tacticalGameData.getName(), tacticalGameData);
        }
    }
}
