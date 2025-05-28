package com.jm.chat.biz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm.chat.biz.dto.ChatDto;
import com.jm.chat.biz.service.ChatService;
import com.jm.chat.framework.kafka.ChatProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatProducer chatProducer;

    private final ChatService chatService;

    @GetMapping("/room-list")
    public ResponseEntity<?> findFriendList(@RequestParam Long userId) {

        return ResponseEntity.ok(chatService.findChatRoomList(userId));
    }

    @GetMapping("/chat-list")
    public ResponseEntity<?> findChatList(@RequestParam Long chatRoomId) {

        return ResponseEntity.ok(chatService.findChatList(chatRoomId));
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@RequestBody ChatDto.Request.ChatRoomCreate chatRoomCreateDto){
        chatService.createChatRoom(chatRoomCreateDto);
        return ResponseEntity.ok("create room success");
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto.Request.ChatMsg msg, SimpMessageHeaderAccessor headerAccessor) throws Exception {

        log.info("📩 받은 메시지: roomId={}, sender={}, message={}", msg.getChatRoomId(), msg.getSenderId(), msg.getMessage());

//        redisTemplate.convertAndSend("chatQueue", msg); // redis
        chatProducer.send("chat-topic", msg);


        messagingTemplate.convertAndSend("/topic/chat/" + msg.getChatRoomId(), msg);

    }



}
