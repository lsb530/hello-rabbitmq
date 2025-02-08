package boki.hellorabbitmq.step3.message

import boki.hellorabbitmq.step3.notification.NotificationMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class StompController(
    private val messagingTemplate: SimpMessagingTemplate,
) {

    @MessageMapping("/send")
    fun sendMessage(notificationMessage: NotificationMessage) {
        // 수신된 메시지를 브로드캐스팅
        val message = notificationMessage.message

        println("[#] message = $message")
        // 클라이언트에 메시지 브로드캐스트
        messagingTemplate.convertAndSend("/topic/notifications", message)
    }
}