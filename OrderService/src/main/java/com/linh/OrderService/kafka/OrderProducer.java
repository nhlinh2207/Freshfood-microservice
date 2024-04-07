package com.linh.OrderService.kafka;

import com.linh.OrderService.model.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProducer {

    private NewTopic topic;

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendMessage(OrderEvent event){
        log.info("Sending order event : "+event.toString());
        Message<OrderEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
