package com.tonir.demo.managers.event;

import com.tonir.demo.managers.API;

public interface EventListener {
    default void registerListener () {
        API.get(EventModule.class).registerListener(this);
    }
}
