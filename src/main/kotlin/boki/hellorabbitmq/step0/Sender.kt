package boki.hellorabbitmq.step0

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Sender(
    private val rabbitTemplate: RabbitTemplate,
) {

    fun sendMessage(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message)
        println("[#] Send: $message")
    }

}