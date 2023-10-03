package com.brvsk.notificationservice.mail;

import com.brvsk.commons.event.OrderMailMessage;

public interface MailSender {
    void send(OrderMailMessage orderMailMessage);
}
