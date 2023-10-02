package com.brvsk.notificationservice.notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotificationTypeNotValid extends RuntimeException{
    public NotificationTypeNotValid(final String message) {
        super(message);
    }
}

