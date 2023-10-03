package com.brvsk.notificationservice.kafka;

import com.brvsk.commons.event.OrderNotificationMessage;
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
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notificationTopic", groupId = "groupId")
    void mailNotificationListener(ConsumerRecord<String, OrderNotificationMessage> record) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(String.valueOf(record.value()));
        String userEmail = jsonNode.get("userEmail").asText();
        String orderTrackingNumber = jsonNode.get("orderTrackingNumber").asText();
        String mailNotificationTypeString = jsonNode.get("mailNotificationTypeString").asText();

        OrderNotificationMessage orderNotificationMessage = new OrderNotificationMessage(userEmail, orderTrackingNumber, mailNotificationTypeString);

        notificationService.sendMailNotification(orderNotificationMessage);
    }

    @KafkaListener(topics = "smsNotificationTopic", groupId = "sms")
    void smsNotificationListener(ConsumerRecord<String, OrderSMSMessage> record) throws JsonProcessingException {

        JsonNode jsonNode = objectMapper.readTree(String.valueOf(record.value()));
        String phoneNumber = jsonNode.get("phoneNumber").asText();
        String orderTrackingNumber = jsonNode.get("orderTrackingNumber").asText();
        String orderSmsTypeString = jsonNode.get("orderSmsTypeString").asText();

        OrderSMSMessage orderSMSMessage = new OrderSMSMessage(phoneNumber, orderTrackingNumber, orderSmsTypeString);
        notificationService.sendSMSNotification(orderSMSMessage);
    }

}
