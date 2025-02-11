package boki.hellorabbitmq.step5

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class LogPublisher(
    private val rabbitTemplate: RabbitTemplate,
) {

    fun publish(routingKey: String, message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_EXCHANGE, routingKey, message)
        println("message published: $routingKey - $message")
    }

}