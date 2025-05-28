package com.jm.chat.framework.socket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompSubscriptionInterceptor implements ChannelInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination(); // 예: /topic/chat/123
            Principal user = accessor.getUser();
            if (destination != null && user != null && destination.startsWith("/topic/chat/")) {
                String roomId = destination.substring("/topic/chat/".length());
                String userId = user.getName();

                // 양방향 이유
                // 정상종료될때는 room -> user, 비정상종료될때 (새로고침, 강제종료) 등이 되면 프론트에서 룸번호를 넘겨줄수가 없음
                // 해서 user -> room 구조도 추가해서 roomId를 추적하도록함
                // room -> user
                redisTemplate.opsForSet().add("topic:chatroom:" + roomId, userId);

                // user -> room
                redisTemplate.opsForSet().add("user:rooms:" + userId, roomId);

                log.info("구독 등록: room={}, user={}", roomId, userId);
            }
        }

        return message;
    }
}
