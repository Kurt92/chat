package com.jm.chat.framework.kafka;


import com.jm.chat.biz.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatProducer {

    private final KafkaTemplate<String, ChatDto.Request.ChatMsg> kafkaTemplate;


    public void send(String topic, ChatDto.Request.ChatMsg msg) {
        kafkaTemplate.send(topic, msg);
    }
}
