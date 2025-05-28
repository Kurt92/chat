package com.jm.chat.framework.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Set;

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
        Set<String> rooms = redisTemplate.opsForSet().members("user:rooms:" + userId);

        if (rooms != null) {
            for (String roomId : rooms) {
                redisTemplate.opsForSet().remove("topic:chatroom:" + roomId, userId);
            }

            // user -> room 정보도 같이 삭제
            redisTemplate.delete("user:rooms:" + userId);
        }

        log.info("연결 해제됨: user={}, rooms={}", userId, rooms);
    }
}
