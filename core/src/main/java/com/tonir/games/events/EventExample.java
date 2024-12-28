package com.tonir.games.events;

import com.tonir.games.managers.API;
import com.tonir.games.managers.event.Event;
import com.tonir.games.managers.event.EventModule;

public class EventExample extends Event {
    public String test;

    public static void fire (String test) {
        final EventModule eventModule = API.get(EventModule.class);
        final EventExample event = eventModule.obtainEvent(EventExample.class);
        event.test = test;
        API.get(EventModule.class).fireEvent(event);
    }
}
