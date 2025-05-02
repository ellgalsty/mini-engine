package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class PetsGameData implements IGameData {
    @Getter
    private final ObjectMap<String, PetGameData> petsMap = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        petsMap.clear();
        final Array<XmlReader.Element> petsXML = rootXml.getChildrenByName("pet");
        for (XmlReader.Element petXML : petsXML) {
            final PetGameData petGameData = new PetGameData();
            petGameData.load(petXML);
            petsMap.put(petGameData.getName(), petGameData);
        }
    }
}
