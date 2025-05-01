package com.jm.chat.biz.entity;

import com.jm.chat.biz.dto.ChatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {

    List<ChatDto.Response.ChatMsg> findByChatRoom_ChatRoomId(Long chatRoomId);
}
