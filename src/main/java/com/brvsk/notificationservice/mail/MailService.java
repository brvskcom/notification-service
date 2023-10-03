package com.brvsk.notificationservice.mail;

import com.brvsk.commons.event.OrderMailMessage;
import com.brvsk.commons.event.OrderMailType;
import com.brvsk.notificationservice.notification.NotificationTypeNotValid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService implements MailSender{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void send(OrderMailMessage orderMailMessage) {
        String userEmail = orderMailMessage.getUserEmail();
        String orderTrackingNumber = orderMailMessage.getOrderTrackingNumber();
        String mailNotificationTypeString = orderMailMessage.getOrderMailTypeString();
        OrderMailType orderMailType = OrderMailType.valueOf(mailNotificationTypeString);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(orderMailType.getMailTitle());
        simpleMailMessage.setText(buildEmail(orderMailType, orderTrackingNumber));

        javaMailSender.send(simpleMailMessage);
    }



    private String buildEmail(OrderMailType orderMailType, String orderTrackingNumber){
        switch (orderMailType){
            case ORDER_PLACED -> {
                return buildOrderPlacedEmail(orderTrackingNumber);
            }

            case ORDER_PAID -> {
                return buildOrderPaidEmail(orderTrackingNumber);
            }

            case ORDER_SHIPPED -> {
                return buildOrderShippedEmail(orderTrackingNumber);
            }

            case ORDER_RECEIVED -> {
                return buildOrderReceivedEmail(orderTrackingNumber);
            }
            default -> throw new NotificationTypeNotValid("Notification type is not valid");
        }
    }

    private String buildOrderPlacedEmail(String orderTrackingNumber) {
        return "Hello! " +
                " \n your order has been placed " +
                " it is your tracking number: " + orderTrackingNumber;
    }
    private String buildOrderPaidEmail(String orderTrackingNumber) {
        return "Hello! " +
                " \n your order has been paid " +
                " it is your tracking number: " + orderTrackingNumber;
    }

    private String buildOrderShippedEmail(String orderTrackingNumber) {
        return "Hello! " +
                " \n your order has been shipped " +
                " it is your tracking number: " + orderTrackingNumber;
    }

    private String buildOrderReceivedEmail(String orderTrackingNumber) {
        return "Hello! " +
                " \n your order with order tracking number: "+ orderTrackingNumber+" has been received :)" ;
    }
}
