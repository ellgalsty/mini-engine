package com.tonir.demo.events.example;

import com.tonir.demo.managers.API;
import com.tonir.demo.managers.event.Event;
import com.tonir.demo.managers.event.EventModule;

public class EventExample extends Event {
    public String test;

    public static void fire (String test) {
        final EventModule eventModule = API.get(EventModule.class);
        final EventExample event = eventModule.obtainEvent(EventExample.class);
        event.test = test;
        API.get(EventModule.class).fireEvent(event);
    }
}
