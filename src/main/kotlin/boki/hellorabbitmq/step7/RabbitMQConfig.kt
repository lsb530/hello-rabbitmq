package boki.hellorabbitmq.step7

import org.springframework.amqp.core.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val ORDER_COMPLETED_QUEUE = "order_completed_queue"
        const val ORDER_EXCHANGE = "order_completed_exchange"
        const val DLQ = "deadLetterQueue"
        const val DLX = "deadLetterExchange"
    }

    @Bean
    fun orderExchange(): TopicExchange {
        return TopicExchange(ORDER_EXCHANGE)
    }

    @Bean
    fun deadLetterExchange(): TopicExchange {
        return TopicExchange(DLX)
    }

    // 메시지가 처리되지 못했을 경우 자동으로 DLQ로 이동시킴
    @Bean
    fun orderQueue(): Queue {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX) // Dead Letter EXCHANGE 설정
            .withArgument("x-dead-letter-routing-key", DLQ) // Dead Letter EXCHANGE 설정
            .ttl(5000)
            .build()
    }

    @Bean
    fun deadLetterQueue(): Queue {
        return Queue(DLQ)
    }

    @Bean
    fun orderCompletedBinding(): Binding {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed.#")
    }

    @Bean
    fun deadLetterBinding(): Binding {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ)
    }

}