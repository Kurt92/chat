package com.jm.chat.framework.socket.conf;


import com.jm.chat.framework.socket.CustomHandshakeHandler;
import com.jm.chat.framework.socket.interceptor.HttpHandshakeInterceptor;
import com.jm.chat.framework.socket.interceptor.StompSubscriptionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    private final HttpHandshakeInterceptor httpHandshakeInterceptor;
    private final CustomHandshakeHandler customHandshakeHandler;
    private final StompSubscriptionInterceptor stompSubscriptionInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(httpHandshakeInterceptor)
                .setHandshakeHandler(customHandshakeHandler)
                .withSockJS(); // SockJS fallback 지원 (JS 클라이언트 편의용)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 서버에서 컨트롤러로 메시지를 보낼 때 사용
        registry.setApplicationDestinationPrefixes("/pub");

        // 클라이언트에서 구독(subscribe)할 prefix
        registry.enableSimpleBroker("/topic"); // 메모리 브로커 사용

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompSubscriptionInterceptor);
    }
}
