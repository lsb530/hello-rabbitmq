package boki.hellorabbitmq.step10

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
        @RequestParam testCase: Boolean
    ): ResponseEntity<String> {
        println("Publisher Confirms Send message : $stockEntity")

        try {
            messageProducer.sendMessage(stockEntity, testCase)
            return ResponseEntity.ok("Publisher Confirms sent successfully")
        } catch (e: java.lang.RuntimeException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Publisher Confirms 트랜잭션 실패 : " + e.message)
        }
    }
}