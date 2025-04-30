package com.jm.chat.framework.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatConsumer {


    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(String message) {
        log.info("📥 Kafka 메시지 수신: {}", message);
        // 여기에 DB 저장 로직 삽입
    }

}
