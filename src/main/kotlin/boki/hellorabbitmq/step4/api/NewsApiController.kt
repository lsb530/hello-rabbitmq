package boki.hellorabbitmq.step4.api

import boki.hellorabbitmq.step4.notification.NewsPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/news")
class NewsApiController(
    private val newsPublisher: NewsPublisher
) {

    @PostMapping("/publish")
    fun publishNews(@RequestParam("newsType") newsType: String): ResponseEntity<String> {
        val result = newsPublisher.publishAPI(newsType)
        return ResponseEntity.ok("# Message published to RabbitMQ: $result")
    }

    // curl -X POST "http://localhost:8080/api/news/publish?newsType=java" -H "Content-Type: application/json"
    // curl -X POST "http://localhost:8080/api/news/publish?newsType=spring" -H "Content-Type: application/json"
    // curl -X POST "http://localhost:8080/api/news/publish?newsType=vue" -H "Content-Type: application/json"

}