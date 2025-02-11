package boki.hellorabbitmq.step6

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
            "log.error"
        } else if (e is IllegalArgumentException) {
            "log.warn"
        } else {
            "log.error"
        }

        println("routingKey = ${routingKey}")

        logPublisher.publish(routingKey, "Exception occurred: $message")
    }

    // Message
    fun handleMessage(message: String) {
        val routingKey = "log.info"
        logPublisher.publish(routingKey, "Info Log: $message")
    }

}