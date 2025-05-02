package com.bootcamp.demo.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;

public class Utils {

    public static <K, V> K getRandomKey (ObjectMap<K, V> map) {
        if (map.size == 0) return null;

        int randomIndex = MathUtils.random(map.size - 1);
        int currentIndex = 0;

        for (ObjectMap.Entry<K, V> entry : map.entries()) {
            if (currentIndex == randomIndex) {
                return entry.key;
            }
            currentIndex++;
        }

        return null; // should never be reached
    }
}
