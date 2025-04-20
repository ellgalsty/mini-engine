package com.bootcamp.demo.data.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.bootcamp.demo.data.MilitaryGearSlot;
import lombok.Getter;

import java.util.Locale;

public class MilitaryGearsGameData implements IGameData {
    @Getter
    private final ObjectMap<MilitaryGearSlot, ObjectMap<String, MilitaryGearGameData>> militarySlotsWithGears = new ObjectMap<>();

    @Override
    public void load (XmlReader.Element rootXml) {
        militarySlotsWithGears.clear();
        for (MilitaryGearSlot slot : MilitaryGearSlot.values) {
            final XmlReader.Element slotXml = rootXml.getChildByName(slot.name().toLowerCase(Locale.ENGLISH));
            final ObjectMap<String, MilitaryGearGameData> militaryGameData = new ObjectMap<>();
            final Array<XmlReader.Element> itemsXml = slotXml.getChildrenByName("item");
            for (XmlReader.Element itemXml : itemsXml) {
                final MilitaryGearGameData gearGameData = new MilitaryGearGameData();
                gearGameData.load(itemXml);
                militaryGameData.put(gearGameData.getName(), gearGameData);
            }
            militarySlotsWithGears.put(slot, militaryGameData);
        }
    }
}
