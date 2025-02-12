package boki.hellorabbitmq.step8_2

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val ORDER_COMPLETED_QUEUE = "orderCompletedQueue"
        const val DLQ = "deadLetterQueue"
        const val ORDER_TOPIC_EXCHANGE = "orderExchange"
        const val ORDER_TOPIC_DLX = "deadLetterExchange"
        const val DEAD_LETTER_ROUTING_KEY = "dead.letter"
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_EXCHANGE)
    }

    @Bean
    fun deadLetterExchange(): TopicExchange {
        return TopicExchange(ORDER_TOPIC_DLX)
    }

    @Bean
    fun queue(): Queue {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
            .withArgument("x-dead-letter-exchange", ORDER_TOPIC_DLX)
            .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
            .build()
    }

    @Bean
    fun deadLetterQueue(): Queue {
        return Queue(DLQ)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder.bind(queue()).to(exchange()).with("order.completed.*");
    }

    @Bean
    fun deadLetterQueueBinding(): Binding {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY)
    }
}