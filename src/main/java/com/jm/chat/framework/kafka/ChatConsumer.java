package com.jm.chat.framework.kafka;


import com.jm.chat.biz.dto.ChatDto;
import com.jm.chat.biz.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatConsumer {

    private final ChatService chatService;

    @KafkaListener(topics = "chat-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ChatDto.Request.ChatMsg msg) {

        // db인서트
        chatService.saveMessage(msg);


    }

}
