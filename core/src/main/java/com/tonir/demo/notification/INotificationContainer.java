package com.tonir.demo.notification;

public interface INotificationContainer {

    void addNotificationWidget(NotificationWidget widget);

    boolean isShowNumber();

    boolean isShowNotification();
}
