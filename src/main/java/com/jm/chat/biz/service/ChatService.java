package com.jm.chat.biz.service;

import com.jm.chat.biz.dto.ChatDto;
import com.jm.chat.biz.entity.ChatRoom;
import com.jm.chat.biz.entity.ChatRoomRepository;
import com.jm.chat.biz.repository.ChatRoomQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomQueryDslRepository chatRoomQueryDslRepository;

    public List<ChatDto.Response.ChatRoomList> findChatRoomList(Long userId) {


        List<ChatDto.Response.ChatRoomList> chatRooms = chatRoomQueryDslRepository.findChatRoomList(userId);

        return chatRooms;
    }





}
