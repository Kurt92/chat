package com.jm.chat.framework.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener {

    private final RedisTemplate<String, String> redisTemplate;

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        if (user == null) return;

        String userId = user.getName();

         for (String roomId : redisTemplate.opsForSet().members("user:rooms:" + userId)) {
             redisTemplate.opsForSet().remove("topic:chatroom:" + roomId, userId);
         }

        // 혹은 TTL로 간단하게 관리할 수도 있음
        log.info("연결 해제됨: user={}", userId);
    }
}
