package com.jm.chat.framework.socket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:3000") // 프론트 도메인
                .withSockJS(); // SockJS fallback 지원 (JS 클라이언트 편의용)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 서버에서 컨트롤러로 메시지를 보낼 때 사용
        registry.setApplicationDestinationPrefixes("/pub");

        // 클라이언트에서 구독(subscribe)할 prefix
        registry.enableSimpleBroker("/topic"); // 메모리 브로커 사용

    }
}
