package com.linh.EmailService.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Value("${spring.rabbitmq.jsonQueue}")
    private String jsonQueueName;

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routingKey}")
    private String routingKey;

    @Value("${spring.rabbitmq.jsonRoutingKey}")
    private String jsonRoutingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueueName);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding jsonBinding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(jsonRoutingKey);
    }


//  JSON converter
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        ObjectMapper mapper = new ObjectMapper();
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter(mapper));
        return rabbitTemplate;
    }
}
