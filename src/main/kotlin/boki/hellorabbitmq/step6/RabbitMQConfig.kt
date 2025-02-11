package boki.hellorabbitmq.step6

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        const val ERROR_QUEUE = "error_queue"
        const val WARN_QUEUE = "warn_queue"
        const val INFO_QUEUE = "info_queue"
        const val ALL_LOG_QUEUE = "all_log_queue"

        const val DIRECT_EXCHANGE = "direct_exchange"
        const val TOPIC_EXCHANGE = "topic_exchange"
    }

    @Bean
    fun directExchange(): DirectExchange {
        return DirectExchange(DIRECT_EXCHANGE)
    }

    @Bean
    fun topicExchange(): TopicExchange {
        return TopicExchange(TOPIC_EXCHANGE)
    }

    @Bean
    fun errorQueue()= Queue(ERROR_QUEUE, false)

    @Bean
    fun warnQueue()= Queue(WARN_QUEUE, false)

    @Bean
    fun infoQueue()= Queue(INFO_QUEUE, false)

    @Bean
    fun allLogQueue()= Queue(ALL_LOG_QUEUE, false)

    @Bean
    fun errorBinding(): Binding {
        return BindingBuilder.bind(errorQueue()).to(topicExchange()).with("log.error")
    }

    @Bean
    fun warnBinding(): Binding {
        return BindingBuilder.bind(warnQueue()).to(topicExchange()).with("log.warn")
    }

    @Bean
    fun infoBinding(): Binding {
        return BindingBuilder.bind(infoQueue()).to(topicExchange()).with("log.info")
    }

    @Bean
    fun allLogBinding(): Binding {
        return BindingBuilder.bind(allLogQueue()).to(topicExchange()).with("log.*")
    }

}