package com.linh.EmailService.producer;

import com.linh.EmailService.model.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonProducer {

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.jsonRoutingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(EmailDto message){
        log.info("Sending JSON Message -> ("+message.getFirstName()+ " -- "+message.getLastName()+" -- "+message.getEmailContent()+")");
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
