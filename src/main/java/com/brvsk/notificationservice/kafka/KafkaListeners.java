package com.brvsk.notificationservice.kafka;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.commons.event.OrderNotificationMessage;
import com.brvsk.notificationservice.mail.MailSender;
import com.brvsk.notificationservice.notification.Notification;
import com.brvsk.notificationservice.notification.NotificationRepository;
import com.brvsk.notificationservice.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final MailSender mailSender;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "notificationTopic", groupId = "mailNotification")
    void mailNotificationListener(ConsumerRecord<String, OrderNotificationMessage> record) {

        OrderNotificationMessage message = record.value();
        String userEmail = message.userEmail();
        String orderTrackingNumber = message.orderTrackingNumber();
        MailNotificationType mailNotificationType = MailNotificationType.valueOf(message.mailNotificationTypeString());

        mailSender.send(userEmail, mailNotificationType, orderTrackingNumber);

        Notification notification = new Notification(userEmail,orderTrackingNumber, NotificationType.MAIL);
        notificationRepository.save(notification);

    }
}
