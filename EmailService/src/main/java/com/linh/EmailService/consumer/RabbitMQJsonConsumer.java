package com.linh.EmailService.consumer;

import com.linh.EmailService.model.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQJsonConsumer {

    @RabbitListener(queues = {"${spring.rabbitmq.jsonQueue}"})
    public void consume(EmailDto message){
        log.info("JSON Message received -> "+ new String(message.toString()));
    }
}
