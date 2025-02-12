package boki.hellorabbitmq.step9

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderConsumer {

    private var retryCount = 0

    @RabbitListener(queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE])
    fun processMessage(message: String) {
        println("Received message: $message, count: ${retryCount++}")
        if ("fail".equals(message, ignoreCase = true)) {
            throw RuntimeException("- Processing failed. Retry")
        }
        println("Message processed successfully: $message")
        retryCount = 0
    }

}