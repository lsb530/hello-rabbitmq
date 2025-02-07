package boki.hellorabbitmq.step0

import org.springframework.stereotype.Component

@Component
class Receiver {

    fun receiveMessage(message: String) {
        println("[#] Received: $message")
    }

}