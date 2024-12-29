package com.tonir.demo.events;

import com.tonir.demo.managers.event.EventHandler;
import com.tonir.demo.managers.event.EventListener;
import com.tonir.demo.managers.event.EventPriority;

public class EventListenerExample implements EventListener {

    public EventListenerExample () {
        registerListener(); // do not forget to register listener
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEventExample (EventExample event) {
        System.out.println(event.test);
    }
}
