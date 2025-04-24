package com.bootcamp.demo.data;

import lombok.Getter;

public enum MilitaryGearSlot {
    WEAPON ("Weapon"),
    MELEE ("Melee"),
    HEAD ("Head"),
    BODY ("Body"),
    GLOVES ("Gloves"),
    SHOES ("Shoes"),;

    public static final MilitaryGearSlot[] values = values();

    @Getter
    private final String stringValue;

    MilitaryGearSlot (String stringValue) {
        this.stringValue = stringValue;
    }
}
