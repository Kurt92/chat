package com.jm.chat.biz.controller;

import com.jm.chat.biz.dto.ChatDto;
import com.jm.chat.biz.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatContoller {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @GetMapping("/chat/room-list")
    public ResponseEntity<?> findFriendList(@RequestParam Long userId) {

        return ResponseEntity.ok(chatService.findChatRoomList(userId));
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto.Request.ChatMsg msg,
                            SimpMessageHeaderAccessor headerAccessor) {
        messagingTemplate.convertAndSend("/topic/chat/" + msg.getChatRoomId(), msg);
    }



}
