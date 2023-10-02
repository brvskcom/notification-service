package com.brvsk.notificationservice.mail;

import com.brvsk.commons.event.MailNotificationType;

public interface MailSender {
    void send(String to, MailNotificationType mailNotificationType, String orderTrackingNumber);
}
