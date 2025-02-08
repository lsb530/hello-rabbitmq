package boki.hellorabbitmq.step4.notification

import boki.hellorabbitmq.step4.config.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class NewsSubscriber(
    private val messagingTemplate: SimpMessagingTemplate
) {

    @RabbitListener(queues = [RabbitMQConfig.JAVA_QUEUE])
    fun javaNews(message: String) { // 뉴스 브로드캐스트
        messagingTemplate.convertAndSend("/topic/java", message)
    }

    @RabbitListener(queues = [RabbitMQConfig.SPRING_QUEUE])
    fun springNews(message: String) { // 기술 뉴스 브로드캐스트
        messagingTemplate.convertAndSend("/topic/spring", message)
    }

    @RabbitListener(queues = [RabbitMQConfig.VUE_QUEUE])
    fun vueNews(message: String) { // 일반 뉴스 브로드캐스트
        messagingTemplate.convertAndSend("/topic/vue", message)
    }

}