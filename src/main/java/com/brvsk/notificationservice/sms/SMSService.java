package com.brvsk.notificationservice.sms;

import com.brvsk.commons.event.OrderSMSMessage;
import com.brvsk.commons.event.OrderSMSType;
import com.brvsk.notificationservice.notification.NotificationTypeNotValid;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SMSService implements SMSSender{

    private final SMSConfiguration smsConfiguration;

    @Override
    public void sendSMS(OrderSMSMessage orderSMSMessage) {
        PhoneNumber to = new PhoneNumber(orderSMSMessage.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(smsConfiguration.getFromNumber());
        String messageBody = buildSMS(
                orderSMSMessage.getOrderSmsTypeString(),
                orderSMSMessage.getOrderTrackingNumber()
        );
        MessageCreator creator = Message.creator(to,from,messageBody);
        creator.create();



    }

    private String buildSMS(String orderSMSTypeString, String orderTrackingNumber) {
        OrderSMSType orderSMSType = orderSMSTypeMapper(orderSMSTypeString);
        switch (orderSMSType){
            case ORDER_PLACED -> {
                return buildOrderPlacedSMS(orderTrackingNumber);
            }
            case ORDER_SEND -> {
                return buildOrderSendSMS(orderTrackingNumber);
            }
            case ORDER_RECEIVED -> {
                return buildOrderReceivedSMS(orderTrackingNumber);
            }
            default -> throw new NotificationTypeNotValid("SMS type is not valid");
        }
    }

    private String buildOrderPlacedSMS(String orderTrackingNumber){
        return "Hello! " +
                "\n your order has been placed" +
                "\n it is your tracking number: "+ orderTrackingNumber;
    }

    private String buildOrderSendSMS(String orderTrackingNumber){
        return "Hello! " +
                "\n your order has been send" +
                "\n it is your tracking number: "+ orderTrackingNumber;
    }
    private String buildOrderReceivedSMS(String orderTrackingNumber){
        return "Hello! " +
                "\n your order has been received" +
                "\n it is your tracking number: "+ orderTrackingNumber;
    }

    private OrderSMSType orderSMSTypeMapper(String orderSMSTypeString){
        return OrderSMSType.valueOf(orderSMSTypeString);
    }
}
