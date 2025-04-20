package com.bootcamp.demo.data.save;

import lombok.Getter;

public class SaveData {

    @Getter
    private final TacticalsSaveData tacticalsSaveData = new TacticalsSaveData();
    @Getter
    private final MilitaryGearsSaveData militaryGearsSaveData = new MilitaryGearsSaveData();
    @Getter
    private final PetsSaveData petsSaveData = new PetsSaveData();
}
