package com.jm.chat.biz.repository;

import com.jm.chat.biz.dto.ChatDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jm.chat.biz.entity.QChatMsg.chatMsg;
import static com.jm.chat.biz.entity.QChatRoom.chatRoom;

@Repository
@RequiredArgsConstructor
public class ChatRoomQueryDslRepository {

    private final JPAQueryFactory queryFactory;


    public List<ChatDto.Response.ChatRoomList> findChatRoomList(Long userId) {

        List<ChatDto.Response.ChatRoomList> result = queryFactory
                .select(
                        Projections.fields(
                                ChatDto.Response.ChatRoomList.class,
                                chatRoom.chatRoomId,
                                chatRoom.roomNm,
                                chatRoom.targetId,
                                chatMsg.createDt.as("lastMessageTime"),
                                chatMsg.content.as("lastMessage"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(chatMsg.isRead.count())
                                                .from(chatMsg)
                                                .where(
                                                        chatMsg.chatRoom.eq(chatRoom),
                                                       chatMsg.isRead.eq(false)
                                                ), "unreadCount"
                                )
                        )
                )
                .from(chatRoom)
                .innerJoin(chatMsg).on(chatRoom.chatRoomId.eq(chatMsg.chatRoom.chatRoomId))
                .where(
                        chatRoom.userId.eq(userId).or(chatRoom.targetId.eq(userId)),
                        chatMsg.isLastMsg.eq(true)
                )
                .fetch();


        return result;
    }

}
