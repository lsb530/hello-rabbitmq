package boki.hellorabbitmq.step4.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic") // 클라이언트 구독 경로
        config.setApplicationDestinationPrefixes("/app") // 서버 발행 경로
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // WebSocket 엔드포인트
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS()
    }

}