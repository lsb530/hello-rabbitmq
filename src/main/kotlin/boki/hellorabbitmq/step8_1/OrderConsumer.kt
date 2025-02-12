package boki.hellorabbitmq.step8_1

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.retry.RetryContext
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Component


@Component
class OrderConsumer(private val rabbitTemplate: RabbitTemplate, private val retryTemplate: RetryTemplate) {
    @RabbitListener(queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE])
    fun consume(message: String) {
        retryTemplate.execute<Any?, RuntimeException> { context: RetryContext ->
            try {
                println("# Received message: $message # retry: ${context.retryCount}")
                // 실패 조건
                if ("fail".equals(message, ignoreCase = true)) {
                    throw RuntimeException(message)
                }
                println("# 메시지 처리 성공: $message")
            } catch (e: Exception) {
                if (context.retryCount >= 2) {
                    rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_TOPIC_DLX,
                        RabbitMQConfig.DEAD_LETTER_ROUTING_KEY, message
                    )
                } else {
                    throw e
                }
            }
            null
        }
    }
}