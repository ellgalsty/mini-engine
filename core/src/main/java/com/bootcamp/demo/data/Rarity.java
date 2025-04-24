package com.bootcamp.demo.data;

import lombok.Getter;

public enum Rarity {
    RUSTED ("b19987", "847264"),
    SCRAP ("5e97c2", "3c7097"),
    HARDENED ("b792c9", "81628a"),
    ELITE ("e6ad4d", "907932"),
    ASCENDANT ("da5c56", "963431"),
    NUCLEAR ("acd539", "719634"),
    JUGGERNAUT ("796ded", "5241cf"),
    DOMINION ("f4677b", "b5465c"),
    OBLIVION ("52feb3", "28ac8a"),
    IMMORTAL ("65ffdf", "1db089"),
    ETHEREAL ("fdfdcd", "bbfa8b");

    public static final Rarity[] values = Rarity.values();

    @Getter
    private final String backgroundColor;
    @Getter
    private final String borderColor;

    Rarity(String background, String border) {
        backgroundColor = background;
        borderColor = border;
    }
}
