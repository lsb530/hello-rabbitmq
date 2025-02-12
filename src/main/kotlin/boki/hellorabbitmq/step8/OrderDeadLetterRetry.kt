package boki.hellorabbitmq.step8

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

// 데드레터로 들어온 메시지를 Requeue 한다.
@Component
class OrderDeadLetterRetry(private val rabbitTemplate: RabbitTemplate) {
    @RabbitListener(queues = [RabbitMQConfig.DLQ])
    fun processDeadLetter(inputMessage: String) {
        var message = inputMessage
        println("[DLQ Received]: $message")

        try {
            // "fail" 메시지를 수정하여 성공적으로 처리되도록 변경
            if ("fail".equals(message, ignoreCase = true)) {
                message = "success"
                println("[DLQ] Message fixed: $message")
            } else {
                // 이미 수정된 메시지는 다시 처리하지 않음
                System.err.println("[DLQ] Message already fixed. Ignoring: $message")
                return
            }

            // 수정된 메시지를 원래 큐로 다시 전송
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_TOPIC_EXCHANGE, "order.completed", message)
            println("[DLQ] Message requeued to original queue: $message")
        } catch (e: Exception) {
            System.err.println("[DLQ] Failed to reprocess message: " + e.message)
        }
    }
}