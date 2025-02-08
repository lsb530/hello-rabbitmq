package boki.hellorabbitmq.step2

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class WorkQueueProducer(
    private val rabbitTemplate: RabbitTemplate,
) {

    fun sendWorkQueue(workQueueMessage: String, duration: Int) {
        // val message = "${workQueueMessage}|$duration"
        val message = "${workQueueMessage}| $duration" // 의도적으로 에러 발생
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message)
        println("Sent workqueue: $message")
    }

}