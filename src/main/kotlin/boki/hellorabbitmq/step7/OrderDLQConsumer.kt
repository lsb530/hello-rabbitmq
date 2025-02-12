package boki.hellorabbitmq.step7

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component


@Component
class OrderDLQConsumer(
    private val rabbitTemplate: RabbitTemplate
) {

    @RabbitListener(queues = [RabbitMQConfig.DLQ])
    fun process(message: String) {
        println("DLQ Message Received: $message")

        try {
            // val fixMessage = message.replace("[^a-zA-Z0-9]".toRegex(), "")
            val fixMessage = "success"

            rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                "order.completed.shipping",
                fixMessage
            )
            println("DLQ Message Sent: $fixMessage")
        } catch (e: Exception) {
            System.err.println("### [DLQ Consumer Error] " + e.message)
        }
    }

}