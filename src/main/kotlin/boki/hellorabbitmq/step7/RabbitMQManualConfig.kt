package boki.hellorabbitmq.step7

import org.springframework.amqp.core.AcknowledgeMode
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * RabbitMQ 리스너의 설정을 커스터마이징
 */
@EnableRabbit
@Configuration
class RabbitMQManualConfig {

    @Bean
    fun rabbitListenerContainerFactory(
        connectionFactory: ConnectionFactory,
    ): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        // 수동 모드 설정
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL)
        return factory
    }
}