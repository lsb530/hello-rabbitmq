package boki.hellorabbitmq.step3.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@EnableWebSocketMessageBroker
@Configuration
class WebSocketConfig: WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic") // 클라이언트 구독 경로
        registry.setApplicationDestinationPrefixes("/app") // 서버 발행 경로
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // WebSocket Endpoint
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS()
        // setAllowedOrigins("*") (X) ->setAllowedOriginPatterns("*") (O)
    }
}