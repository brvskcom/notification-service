package com.brvsk.notificationservice.sms;

import com.brvsk.commons.event.OrderSMSMessage;

public interface SMSSender {
    void sendSMS(OrderSMSMessage orderSMSMessage);
}
