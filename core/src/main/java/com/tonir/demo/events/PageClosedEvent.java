package com.tonir.demo.events;

import com.tonir.demo.managers.API;
import com.tonir.demo.managers.event.Event;
import com.tonir.demo.managers.event.EventModule;
import com.tonir.demo.presenters.utils.pages.APage;
import lombok.Getter;

public class PageClosedEvent extends Event {

    @Getter
    private Class<? extends APage> aClass;

    public static void fire (Class<? extends APage> aClass) {
        final PageClosedEvent event = API.get(EventModule.class).obtainEvent(PageClosedEvent.class);
        event.aClass = aClass;
        API.get(EventModule.class).fireEvent(event);
    }
}

