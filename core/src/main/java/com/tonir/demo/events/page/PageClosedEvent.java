package com.tonir.demo.events.page;

import com.tonir.demo.managers.API;
import com.tonir.demo.managers.event.EventModule;
import com.tonir.demo.utils.presenters.pages.APage;

public class PageClosedEvent extends APageEvent {

    public static void fire (Class<? extends APage> pageClass) {
        final PageClosedEvent event = API.get(EventModule.class).obtainEvent(PageClosedEvent.class);
        event.pageClass = pageClass;
        API.get(EventModule.class).fireEvent(event);
    }
}

