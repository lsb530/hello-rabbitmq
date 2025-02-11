package boki.hellorabbitmq.step5

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val ERROR_QUEUE = "error_queue"
        const val WARN_QUEUE = "warn_queue"
        const val INFO_QUEUE = "info_queue"

        const val DIRECT_EXCHANGE = "direct_exchange"
    }

    @Bean
    fun directExchange(): DirectExchange {
        return DirectExchange(DIRECT_EXCHANGE)
    }

    @Bean
    fun errorQueue()= Queue(ERROR_QUEUE, false)

    @Bean
    fun warnQueue()= Queue(WARN_QUEUE, false)

    @Bean
    fun infoQueue()= Queue(INFO_QUEUE, false)

    @Bean
    fun errorBinding(): Binding {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with("error")
    }

    @Bean
    fun warnBinding(): Binding {
        return BindingBuilder.bind(warnQueue()).to(directExchange()).with("warn")
    }

    @Bean
    fun infoBinding(): Binding {
        return BindingBuilder.bind(infoQueue()).to(directExchange()).with("info")
    }

}