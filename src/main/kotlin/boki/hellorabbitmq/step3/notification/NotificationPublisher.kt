package boki.hellorabbitmq.step3.notification

import boki.hellorabbitmq.step3.config.RabbitMQConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class NotificationPublisher(
    private val rabbitTemplate: RabbitTemplate,
) {

    fun publish(message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", message)
        println("[#] Published Notification: $message")
    }

}