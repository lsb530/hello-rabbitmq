package boki.hellorabbitmq.step9

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/message")
class TransactionController(
    private val messageProducer: MessageProducer
) {
    @PostMapping
    fun sendMessage(
        @RequestBody stockEntity: StockEntity,
        @RequestParam(required = false, defaultValue = "success") testCase: String?
    ): ResponseEntity<String> {
        // do something
        println("Send message : $stockEntity")

        try {
            messageProducer.sendMessage(stockEntity, testCase!!)
            return ResponseEntity.ok("Message sent successfully")
        } catch (e: RuntimeException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("MQ 트랜잭션 실패 : " + e.message)
        }
    }
}