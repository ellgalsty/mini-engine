package com.tonir.demo.events.dialog;

import com.tonir.demo.managers.API;
import com.tonir.demo.managers.event.EventModule;
import com.tonir.demo.utils.presenters.dialogs.ADialog;

public class DialogClosedEvent extends ADialogEvent {

    public static void fire (Class<? extends ADialog> dialogClass) {
        final DialogClosedEvent event = API.get(EventModule.class).obtainEvent(DialogClosedEvent.class);
        event.dialogClass = dialogClass;
        API.get(EventModule.class).fireEvent(event);
    }
}
