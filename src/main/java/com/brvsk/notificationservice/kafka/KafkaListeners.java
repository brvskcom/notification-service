package com.brvsk.notificationservice.kafka;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.commons.event.OrderEvent;
import com.brvsk.commons.event.OrderNotificationMessage;
import com.brvsk.notificationservice.mail.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final MailSender mailSender;

    @KafkaListener(topics = "notificationTopic", groupId = "groupId")
    void mailNotificationListener(ConsumerRecord<String, OrderNotificationMessage> record) {
        try {
            System.out.println(record.value() + "ale super ze dziala" +
                    "");
//            OrderEvent orderEvent = record.value();
//            MailNotificationType mailNotificationType = MailNotificationType.valueOf(orderEvent.getMailNotificationType());
//            System.out.println("Listener received: " + orderEvent);
//            mailSender.send(orderEvent.getEmail(), mailNotificationType, orderEvent.getOrderTrackingNumber());
        } catch (SerializationException e) {
            log.error("Deserialization failed: " + e.getMessage(), e);
        }
    }
}
