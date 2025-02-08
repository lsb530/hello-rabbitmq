package boki.hellorabbitmq.step2

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api")
@RestController
class MessageController(
    private val workQueueProducer: WorkQueueProducer
) {

    @PostMapping("/workqueue")
    fun sendMessage(
        @RequestParam("message") message: String,
        @RequestParam("duration") duration: Int
    ): String {
        workQueueProducer.sendWorkQueue(message, duration)
        return "Work Queue sent = $message, ($duration)"
    }

    /*
        curl -X POST "http://localhost:8080/api/workqueue?message=Task1&duration=2000"
        curl -X POST "http://localhost:8080/api/workqueue?message=Task2&duration=4000"
        curl -X POST "http://localhost:8080/api/workqueue?message=Task3&duration=5000"
     */

}