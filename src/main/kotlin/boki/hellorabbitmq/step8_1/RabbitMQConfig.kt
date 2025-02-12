package boki.hellorabbitmq.step8_1

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        // 큐, 교환기, 라우팅 키 이름 정의
        const val ORDER_COMPLETED_QUEUE = "orderCompletedQueue"
        const val DLQ = "deadLetterQueue"
        const val ORDER_TOPIC_EXCHANGE = "orderExchange"
        const val ORDER_TOPIC_DLX = "deadLetterExchange"
        const val DEAD_LETTER_ROUTING_KEY = "dead.letter"
    }

    // 원래 큐에 연결된 Topic Exchange
    @Bean
    fun orderExchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_EXCHANGE)
    }

    // Dead Letter Exchange
    @Bean
    fun deadLetterExchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_DLX)
    }

    // 원래 큐 설정
    @Bean
    fun orderQueue(): Queue {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
            .withArgument("x-dead-letter-exchange", ORDER_TOPIC_DLX) // DLX 설정
            .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY) // DLQ로 이동할 라우팅 키 설정
            .build()
    }

    // Dead Letter Queue 설정
    @Bean
    fun deadLetterQueue(): Queue {
        return QueueBuilder.durable(DLQ).build()
    }

    // 원래 큐와 Exchange 바인딩
    @Bean
    fun orderQueueBinding(): Binding {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed")
    }

    // Dead Letter Queue와 Dead Letter Exchange 바인딩
    @Bean
    fun deadLetterQueueBinding(): Binding {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY)
    }
}