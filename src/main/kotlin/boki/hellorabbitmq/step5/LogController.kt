package boki.hellorabbitmq.step5

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/logs")
class LogController(
    private val exceptionHandler: CustomExceptionHandler
) {

    @GetMapping("/error")
    fun errorAPI(): ResponseEntity<String> {
        try {
            val value: String? = null
            value!!.toByte()
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
        }
        return ResponseEntity.ok("Controller NullPointerException 처리")
    }

    @GetMapping("/warn")
    fun warnAPI(): ResponseEntity<String> {
        try {
            throw IllegalArgumentException("Invalid argument")
        } catch (e: Exception) {
            exceptionHandler.handleException(e)
        }
        return ResponseEntity.ok("Controller IllegalArgumentException 처리")
    }

    @PostMapping("/info")
    fun infoAPI(@RequestBody message: String): ResponseEntity<String> {
        exceptionHandler.handleMessage(message)
        return ResponseEntity.ok("Controller Info log 발송 처리")
    }

}