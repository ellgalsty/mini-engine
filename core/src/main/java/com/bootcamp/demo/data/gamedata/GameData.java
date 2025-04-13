package com.bootcamp.demo.data.gamedata;

import com.badlogic.gdx.utils.Disposable;

public class GameData implements Disposable {
    private final MilitaryGearGameData militaryGearGameData;

    public GameData() {
        militaryGearGameData = new MilitaryGearGameData();
    }

    @Override
    public void dispose () {

    }
}
