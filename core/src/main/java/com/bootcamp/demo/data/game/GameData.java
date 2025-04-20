package com.bootcamp.demo.data.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import lombok.Getter;

public class GameData {

    private final XmlReader xmlReader = new XmlReader();

    @Getter
    private final MilitaryGearsGameData militaryGearsGameData;
    @Getter
    private final TacticalsGameData tacticalsGameData;
    @Getter
    private final PetsGameData petsGameData;
    @Getter
    private final FlagsGameData flagsGameData;

    public GameData () {
        militaryGearsGameData = new MilitaryGearsGameData();
        tacticalsGameData = new TacticalsGameData();
        petsGameData = new PetsGameData();
        flagsGameData = new FlagsGameData();
    }

    public void load () {
        militaryGearsGameData.load(xmlReader.parse(Gdx.files.internal("data/military-gear.xml")));
        tacticalsGameData.load(xmlReader.parse(Gdx.files.internal("data/tacticals.xml")));
        petsGameData.load(xmlReader.parse(Gdx.files.internal("data/pets.xml")));
        flagsGameData.load(xmlReader.parse(Gdx.files.internal("data/flags.xml")));
    }
}
