package boki.hellorabbitmq.step0

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/messages")
@RestController
class MessageController(
    private val sender: Sender,
) {

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: String): String {
        sender.sendMessage(message)
        return "[#] Message sent successfully! $message"
    }

}