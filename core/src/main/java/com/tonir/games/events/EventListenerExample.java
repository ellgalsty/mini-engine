package com.tonir.games.events;

import com.tonir.games.managers.event.EventHandler;
import com.tonir.games.managers.event.EventListener;
import com.tonir.games.managers.event.EventPriority;

public class EventListenerExample implements EventListener {

    public EventListenerExample () {
        registerListener(); // do not forget to register listener
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEventExample (EventExample event) {
        System.out.println(event.test);
    }
}
