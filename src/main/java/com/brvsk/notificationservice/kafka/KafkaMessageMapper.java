package com.brvsk.notificationservice.kafka;

import com.brvsk.commons.event.OrderMailMessage;
import com.brvsk.commons.event.OrderSMSMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageMapper {

    private final ObjectMapper objectMapper;

    public KafkaMessageMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected OrderMailMessage orderMailMessageMapper(ConsumerRecord<String, OrderMailMessage> record) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(String.valueOf(record.value()));
        String userEmail = jsonNode.get("userEmail").asText();
        String orderTrackingNumber = jsonNode.get("orderTrackingNumber").asText();
        String mailNotificationTypeString = jsonNode.get("orderMailTypeString").asText();

        return new OrderMailMessage(userEmail, orderTrackingNumber, mailNotificationTypeString);
    }

    protected OrderSMSMessage orderSMSMessageMapper(ConsumerRecord<String, OrderSMSMessage> record) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(String.valueOf(record.value()));
        String phoneNumber = jsonNode.get("phoneNumber").asText();
        String orderTrackingNumber = jsonNode.get("orderTrackingNumber").asText();
        String orderSmsTypeString = jsonNode.get("orderSmsTypeString").asText();

        return new OrderSMSMessage(phoneNumber, orderTrackingNumber, orderSmsTypeString);
    }
}
