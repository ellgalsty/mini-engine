package com.bootcamp.demo.data;

import lombok.Getter;

public enum Rarity {
    RUSTED ("b19987", "847264", "Rusted"),
    SCRAP ("5e97c2", "3c7097", "Scrap"),
    HARDENED ("b792c9", "81628a", "Hardened"),
    ELITE ("e6ad4d", "907932", "Elite"),
    ASCENDANT ("da5c56", "963431", "Ascendant"),
    NUCLEAR ("acd539", "719634", "Nuclear"),
    JUGGERNAUT ("796ded", "5241cf", "Juggernaut"),
    DOMINION ("f4677b", "b5465c", "Dominion"),
    OBLIVION ("52feb3", "28ac8a", "Oblivion"),
    IMMORTAL ("65ffdf", "1db089", "Immortal"),
    ETHEREAL ("fdfdcd", "bbfa8b", "Ethereal"),;

    public static final Rarity[] values = Rarity.values();

    @Getter
    private final String backgroundColor;
    @Getter
    private final String borderColor;
    @Getter
    private final String stringValue;

    Rarity(String background, String border, String stringValue) {
        backgroundColor = background;
        borderColor = border;
        this.stringValue = stringValue;
    }
}
