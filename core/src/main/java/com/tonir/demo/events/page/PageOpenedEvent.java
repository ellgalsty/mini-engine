package com.tonir.demo.events.page;

import com.tonir.demo.managers.API;
import com.tonir.demo.managers.event.EventModule;
import com.tonir.demo.utils.presenters.pages.APage;

public class PageOpenedEvent extends APageEvent {

    public static void fire (Class<? extends APage> pageClass) {
        final PageOpenedEvent event = API.get(EventModule.class).obtainEvent(PageOpenedEvent.class);
        event.pageClass = pageClass;
        API.get(EventModule.class).fireEvent(event);
    }
}

