package com.springboot.kafkaproducer.controllers;

import com.springboot.kafkaproducer.services.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){
        this.producer.sendMessage(message);
    }

    @PostMapping("/publish/{name}")
    public String post(@PathVariable("name") final String name) {
        this.producer.sendMessageToUser(name);
        return "Published UserData SuccessFully";
    }
}

