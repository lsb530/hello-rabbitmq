package boki.hellorabbitmq.step5

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class LogConsumer {

    @RabbitListener(queues = [RabbitMQConfig.ERROR_QUEUE])
    fun consumerError(message: String) {
        println("[ERROR] received: $message")
    }

    @RabbitListener(queues = [RabbitMQConfig.WARN_QUEUE])
    fun consumerWarn(message: String) {
        println("[WARN] received: $message")
    }

    @RabbitListener(queues = [RabbitMQConfig.INFO_QUEUE])
    fun consumerInfo(message: String) {
        println("[INFO] received: $message")
    }

}