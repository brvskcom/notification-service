package com.brvsk.notificationservice.notification;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.commons.event.OrderNotificationMessage;
import com.brvsk.commons.event.OrderSMSMessage;
import com.brvsk.notificationservice.mail.MailSender;
import com.brvsk.notificationservice.sms.SMSSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailSender mailSender;
    private final SMSSender smsSender;
    private final NotificationRepository notificationRepository;

    public void sendMailNotification(OrderNotificationMessage orderNotificationMessage){

        String userEmail = orderNotificationMessage.getUserEmail();
        String orderTrackingNumber = orderNotificationMessage.getOrderTrackingNumber();
        String mailNotificationTypeString = orderNotificationMessage.getMailNotificationTypeString();
        MailNotificationType mailNotificationType = MailNotificationType.valueOf(mailNotificationTypeString);

        mailSender.send(userEmail, mailNotificationType, orderTrackingNumber);

        Notification notification = new Notification(
                userEmail,
                orderTrackingNumber,
                NotificationType.MAIL
        );
        notificationRepository.save(notification);
    }

    public void sendSMSNotification(OrderSMSMessage orderSMSMessage){
        smsSender.sendSMS(orderSMSMessage);

        Notification notification = new Notification(
                orderSMSMessage.getPhoneNumber(),
                orderSMSMessage.getOrderTrackingNumber(),
                NotificationType.SMS
        );
        notificationRepository.save(notification);
    }
}
