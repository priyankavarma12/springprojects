package com.springboot.kafkaproducer.services;

import com.springboot.kafkaproducer.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC = "users";
    private static final String TOPIC_JSON = "users_json";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, User> kafkaTemplateUser;

    public void sendMessage(String message){
        log.info(String.format("$$ -> Producing message --> %s", message));
        this.kafkaTemplate.send( TOPIC, message );
    }

    public String sendMessageToUser(String name ){
        log.info(String.format("$$ -> Producing message --> %s", name));
        this.kafkaTemplateUser.send(TOPIC_JSON, new User(name,  "Hyderabad"));
        return "Published successfully";
    }

}
