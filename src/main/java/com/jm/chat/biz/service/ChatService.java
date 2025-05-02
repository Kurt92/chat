package com.jm.chat.biz.service;

import com.jm.chat.biz.dto.ChatDto;
import com.jm.chat.biz.entity.ChatMsg;
import com.jm.chat.biz.entity.ChatMsgRepository;
import com.jm.chat.biz.entity.ChatRoom;
import com.jm.chat.biz.entity.ChatRoomRepository;
import com.jm.chat.biz.repository.ChatQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMsgRepository chatMsgRepository;
    private final ChatQueryDslRepository chatQueryDslRepository;

    public List<ChatDto.Response.ChatRoomList> findChatRoomList(Long userId) {


        List<ChatDto.Response.ChatRoomList> chatRooms = chatQueryDslRepository.findChatRoomList(userId);

        return chatRooms;
    }

    public List<ChatDto.Response.ChatMsg> findChatList(Long chatRoomId) {


        List<ChatDto.Response.ChatMsg> chatMsgs = chatQueryDslRepository.findChatList(chatRoomId);

        return chatMsgs;
    }

    public void saveMessage(ChatDto.Request.ChatMsg msg) {

        Optional<ChatMsg> findLastMsg = chatMsgRepository.findByChatRoom_ChatRoomIdAndIsLastMsgTrue(msg.getChatRoomId());

        findLastMsg.ifPresent(lastMsg -> {
            lastMsg.setIsLastMsg(false);
            chatMsgRepository.save(lastMsg);
        });


        ChatMsg entity = ChatMsg.builder()
                .chatRoom(ChatRoom.builder().chatRoomId(msg.getChatRoomId()).build())
                .userId(msg.getSenderId())
                .accountId(msg.getUserName())
                .content(msg.getMessage())
                .createDt(LocalDateTime.now())
                .isLastMsg(true)
                .build();
        chatMsgRepository.save(entity);

    }



}
