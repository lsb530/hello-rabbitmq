package boki.hellorabbitmq.step10

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME = "transactionQueue"
        const val EXCHANGE_NAME = "transactionExchange"
        const val ROUTING_KEY = "transactionRoutingKey"
    }

    // Queue 설정
    @Bean
    fun transactionQueue(): Queue {
        return QueueBuilder.durable(QUEUE_NAME)
            .withArgument("x-dead-letter-exchange", "") // Error
            .withArgument("x-dead-letter-routing-key", "deadLetterQueue") // Dead Letter Routing Key
            .build()
    }

    // Dead Letter Queue 설정
    @Bean
    fun deadLetterQueue(): Queue {
        return Queue("deadLetterQueue")
    }

    // Exchange 설정
    @Bean
    fun transactionExchange(): DirectExchange {
        return DirectExchange(EXCHANGE_NAME)
    }

    // Binding 설정
    @Bean
    fun transactionBinding(transactionQueue: Queue?, transactionExchange: DirectExchange?): Binding {
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(ROUTING_KEY)
    }

    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    // TODO RabbitTemplate 설정, ReturnsCallback 활성화 등록, ConfirmCallback 설정
    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = messageConverter() // JSON 변환기
        rabbitTemplate.setMandatory(true) // ReturnCallback 활성화

        // confirmCallBack 설정
        rabbitTemplate.setConfirmCallback { correlationData: CorrelationData?, ack: Boolean, cause: String? ->
            if (ack) {
                println("#### [Message confirmed]: ${correlationData?.id ?: "null"}")
            } else {
                println("#### [Message not confirmed]: ${correlationData?.id ?: "null"}, Reason: $cause")
                // 실패 메시지에 대한 추가 처리 로직 (예: 로그 기록, DB 적재, 관리자 알림 등)
            }
        }

        // ReturnCallback 설정
        rabbitTemplate.setReturnsCallback { returned: ReturnedMessage ->
            println("Return Message: " + returned.message.body)
            println("Exchange : " + returned.exchange)
            println("RoutingKey : " + returned.routingKey)
        }
        return rabbitTemplate
    }

    // TODO RabbitListener 설정, 수동 Ack 모드 설정
    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(messageConverter())
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL) // 수동 Ack 모드
        return factory
    }
}