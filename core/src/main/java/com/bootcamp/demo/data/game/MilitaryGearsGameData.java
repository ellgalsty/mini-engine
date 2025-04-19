package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

public class MilitaryGearsGameData implements IGameData {

    private ObjectMap<MilitaryGearSlot, ObjectMap<String, MilitaryGearsGameData>> militaryGearsSlotMap;

    @Override
    public void load (XmlReader.Element rootXml) {

    }
}
