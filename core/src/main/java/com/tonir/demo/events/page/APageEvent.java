package com.tonir.demo.events.page;

import com.tonir.demo.managers.event.Event;
import com.tonir.demo.utils.presenters.widgets.pages.APage;
import lombok.Getter;

@Getter
public class APageEvent extends Event {

    protected Class<? extends APage> pageClass;

    @Override
    public void reset () {
        super.reset();
        pageClass = null;
    }
}
