package boki.hellorabbitmq.step4.notification

import boki.hellorabbitmq.step4.config.RabbitMQConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class NewsPublisher(
    private val rabbitTemplate: RabbitTemplate
) {

    private fun publishMessage(news: String, messageSuffix: String): String {
        val message = news + messageSuffix
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_FOR_NEWS, news, message)
        println("News published: $message")
        return message
    }

    fun publish(news: String)
        = publishMessage(news, " 관련 새 소식이 있어요!")

    fun publishAPI(news: String)
        = publishMessage(news, " - 관련 새 소식이 나왔습니다. (API)")

}