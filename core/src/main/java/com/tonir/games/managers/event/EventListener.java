package com.tonir.games.managers.event;

import com.tonir.games.managers.API;

public interface EventListener {
    default void registerListener () {
        API.get(EventModule.class).registerListener(this);
    }
}
