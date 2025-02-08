package boki.hellorabbitmq.step1

import org.springframework.stereotype.Component

@Component
class Receiver {

    fun receiveMessage(message: String) {
        println("[#] Received: $message")
    }

}