package boki.hellorabbitmq.step4.controller

import boki.hellorabbitmq.step4.notification.NewsPublisher
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class NewsController(
    private val newsPublisher: NewsPublisher
) {
    @MessageMapping("/subscribe") // /app/subscribe
    fun handleSubscribe(@Header("newsType") newsType: String) {
        println("[#] newsType: $newsType")

        val newsMessage: String = newsPublisher.publish(newsType)
        println("# newsMessage: $newsMessage")
    }
}