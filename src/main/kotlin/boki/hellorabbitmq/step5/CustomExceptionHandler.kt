package boki.hellorabbitmq.step5

import org.springframework.stereotype.Component

@Component
class CustomExceptionHandler(
    private val logPublisher: LogPublisher
) {

    // Error or Log
    fun handleException(e: Exception) {
        val message = e.message
        var routingKey: String? = null

        routingKey = if (e is NullPointerException) {
            "error"
        } else if (e is IllegalArgumentException) {
            "warn"
        } else {
            "error"
        }

        logPublisher.publish(routingKey, "Exception occurred: $message")
    }

    // Message
    fun handleMessage(message: String) {
        val routingKey = "info"
        logPublisher.publish(routingKey, "Info Log: $message")
    }

}