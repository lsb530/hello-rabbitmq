package boki.hellorabbitmq.step3.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val QUEUE_NAME = "notificationQueue"
        const val FANOUT_EXCHANGE = "notificationExchange"
    }

    @Bean
    fun queue() = Queue(QUEUE_NAME, false) // volatile

    @Bean
    fun fanoutExchange() = FanoutExchange(FANOUT_EXCHANGE) // Broadcast

    @Bean
    fun bindNotification(notificationQueue: Queue, fanoutExchange: FanoutExchange): Binding {
        // bind().to()를 통해 Queue/Exchange 연결
        return BindingBuilder.bind(notificationQueue).to(fanoutExchange)
    }

}