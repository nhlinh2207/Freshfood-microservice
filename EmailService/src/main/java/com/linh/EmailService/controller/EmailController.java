package com.linh.EmailService.controller;

import com.linh.EmailService.model.EmailDto;
import com.linh.EmailService.producer.RabbitMQJsonProducer;
import com.linh.EmailService.producer.RabbitMQProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/email")
public class EmailController {

    private final RabbitMQProducer rabbitMQProducer;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @PostMapping(path = "/publish")
    public ResponseEntity<String> send(@RequestParam String message){
        rabbitMQProducer.send(message);
        return ResponseEntity.ok("Sent message to RabbitMQ successfully !");
    }

    @PostMapping(path = "/json/publish")
    public ResponseEntity<String> send(@RequestBody EmailDto message){
        rabbitMQJsonProducer.sendJsonMessage(message);
        return ResponseEntity.ok("Sent JSON message to RabbitMQ successfully !");
    }
}
