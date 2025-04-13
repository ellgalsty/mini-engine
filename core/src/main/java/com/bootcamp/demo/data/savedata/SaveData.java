package com.bootcamp.demo.data.savedata;

import com.badlogic.gdx.utils.Disposable;

public class SaveData implements Disposable {
    private final MilitaryGearSaveData militaryGearSaveData;

    public SaveData() {
        militaryGearSaveData = new MilitaryGearSaveData();
    }
    @Override
    public void dispose () {

    }
}
