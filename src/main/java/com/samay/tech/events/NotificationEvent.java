package com.samay.tech.events;

import org.springframework.context.ApplicationEvent;

import com.samay.tech.model.Notification;

/**
 * @author Arquitectura
 */
public final class NotificationEvent extends ApplicationEvent {

    private final Notification notification;

    public NotificationEvent(Object source, Notification notification) {
        super(source);
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }

}
