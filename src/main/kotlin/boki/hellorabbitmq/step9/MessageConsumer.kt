package boki.hellorabbitmq.step9

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer(
    private val stockRepository: StockRepository
) {
    @RabbitListener(queues = ["transactionQueue"])
    fun receiveTransaction(stockEntity: StockEntity) {
        println("# received message  = $stockEntity")

        try {
            stockEntity.updateProcessed(true)
            stockEntity.invokeUpdated()
            stockRepository.save(stockEntity) // 상태 업데이트
            println("# StockEntity 저장 완료 ")
        } catch (e: Exception) {
            println("# Entity 수정 에러 " + e.message)
            throw e // todo: 메시지를 DLQ에 추가
        }
    }
}