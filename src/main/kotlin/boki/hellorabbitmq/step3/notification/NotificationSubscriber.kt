package boki.hellorabbitmq.step3.notification

import boki.hellorabbitmq.step3.config.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class NotificationSubscriber(
    private val simpleMessagingTemplate: SimpMessagingTemplate
) {

    companion object {
        const val CLIENT_URL = "/topic/notifications"
    }

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME])
    fun subscriber(message: String) {
        // RabbitMQ Queue에서 메시지 수신
        // String message = (String) rabbitTemplate.receivedAndConvert(RabbitMQConfig.QUEUE_NAME)
        println("Received Notification: $message")
        // Broadcast
        simpleMessagingTemplate.convertAndSend(CLIENT_URL, message)
    }

}