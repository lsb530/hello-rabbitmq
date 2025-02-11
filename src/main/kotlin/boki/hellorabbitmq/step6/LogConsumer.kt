package boki.hellorabbitmq.step6

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class LogConsumer {

    @RabbitListener(queues = [RabbitMQConfig.ERROR_QUEUE])
    fun consumeError(message: String) {
        println("[ERROR] received: $message")
    }

    @RabbitListener(queues = [RabbitMQConfig.WARN_QUEUE])
    fun consumeWarn(message: String) {
        println("[WARN] received: $message")
    }

    @RabbitListener(queues = [RabbitMQConfig.INFO_QUEUE])
    fun consumeInfo(message: String) {
        println("[INFO] received: $message")
    }

    @RabbitListener(queues = [RabbitMQConfig.ALL_LOG_QUEUE])
    fun consumeAllLog(message: String) {
        println("[ALL LOG] received: $message")
    }

}