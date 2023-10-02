package com.brvsk.notificationservice.mail;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.notificationservice.notification.NotificationTypeNotValid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class MailService implements MailSender{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void send(String to, MailNotificationType mailNotificationType, String orderTrackingNumber) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(mailNotificationType.getMailTitle());
        simpleMailMessage.setText(buildEmail(mailNotificationType, orderTrackingNumber));

        javaMailSender.send(simpleMailMessage);
    }



    private String buildEmail(MailNotificationType mailNotificationType, String orderTrackingNumber){
        switch (mailNotificationType){
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
