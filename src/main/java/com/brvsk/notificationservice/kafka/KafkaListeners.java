package com.brvsk.notificationservice.kafka;

import com.brvsk.commons.event.OrderMailMessage;
import com.brvsk.commons.event.OrderSMSMessage;
import com.brvsk.notificationservice.notification.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final NotificationService notificationService;
    private final KafkaMessageMapper kafkaMessageMapper;

    @KafkaListener(topics = "mailNotificationTopic", groupId = "mail")
    void mailNotificationListener(ConsumerRecord<String, OrderMailMessage> record) throws JsonProcessingException {

        OrderMailMessage orderMailMessage = kafkaMessageMapper.orderMailMessageMapper(record);

        notificationService.sendMailNotification(orderMailMessage);
    }

    @KafkaListener(topics = "smsNotificationTopic", groupId = "sms")
    void smsNotificationListener(ConsumerRecord<String, OrderSMSMessage> record) throws JsonProcessingException {

        OrderSMSMessage orderSMSMessage = kafkaMessageMapper.orderSMSMessageMapper(record);

        notificationService.sendSMSNotification(orderSMSMessage);
    }


}
