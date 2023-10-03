package com.brvsk.notificationservice.notification;

import com.brvsk.commons.event.OrderMailMessage;
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

    public void sendMailNotification(OrderMailMessage orderMailMessage){
        mailSender.send(orderMailMessage);

        Notification notification = new Notification(
                orderMailMessage.getUserEmail(),
                orderMailMessage.getOrderTrackingNumber(),
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
