package boki.hellorabbitmq.step8_2

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class OrderDeadLetterRetry(
    private val rabbitTemplate: RabbitTemplate
) {

    @RabbitListener(queues = [RabbitMQConfig.DLQ])
    fun processDlqMessage(failedMessage: String) {
        try {
            println("[DLQ Received]: $failedMessage")

            // 실패한 메시지를 성공 메시지로 변경
            val message = "success"

            // 수정된 메시지를 원래 큐로 재전송
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
                "order.completed.shipping",
                message
            )

            println("Message successfully reprocessed : $message")
        } catch (e: Exception) {
            System.err.println("Error processing DLQ message : $e")
        }
    }

}