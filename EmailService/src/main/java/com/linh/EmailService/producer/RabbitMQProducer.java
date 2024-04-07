package com.linh.EmailService.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQProducer {

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message){
        log.info("Sending messsage -> "+message);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
