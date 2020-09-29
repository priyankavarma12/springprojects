package com.springboot.kafkaproducer.services;

import com.springboot.kafkaproducer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "users", groupId = "group_id")
    public void consume(String message){
        log.info(String.format("$$ -> Consumed Message -> %s",message));
    }


    @KafkaListener(topics = "users_json", groupId = "group_json")
    public void consumeJson(User user){
        log.info(String.format("$$ -> Consumed JSON Message $$" + user));
    }

}
