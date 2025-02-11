package boki.hellorabbitmq.step6

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class LogPublisher(
    private val rabbitTemplate: RabbitTemplate,
) {

    fun publish(routingKey: String, message: String) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE, routingKey, message)
        println("message published: $routingKey - $message")
    }

}