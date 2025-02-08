package boki.hellorabbitmq.step3.api

import boki.hellorabbitmq.step3.notification.NotificationPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val publisher: NotificationPublisher
) {
    @PostMapping
    fun sendNotification(@RequestBody message: String): String {
        publisher.publish(message)
        return "[#] Notification sent: ${message}\n"
    }
}