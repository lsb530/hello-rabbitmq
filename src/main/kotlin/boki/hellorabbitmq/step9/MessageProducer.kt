package boki.hellorabbitmq.step9

import com.rabbitmq.client.Channel
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MessageProducer(
    private val stockRepository: StockRepository,
    private val rabbitTemplate: RabbitTemplate
) {

    @Transactional
    fun sendMessage(stockEntity: StockEntity, testCase: String) {
        rabbitTemplate.execute<Any> { channel: Channel ->
            try {
                channel.txSelect() // 트랜잭션 시작
                stockEntity.updateProcessed(false)
                val stockEntitySaved = stockRepository.save(stockEntity)

                println("Stock Saved : $stockEntitySaved")

                // 메시지 발행
                rabbitTemplate.convertAndSend("transactionQueue", stockEntitySaved)

                if ("fail".equals(testCase, ignoreCase = true)) {
                    throw RuntimeException("트랜잭션 작업중 에러 발생")
                }

                channel.txCommit()
                println("트랜잭션이 정상적으로 처리 되었음!~")
            } catch (e: Exception) {
                println("트랜잭션 실패 : " + e.message)
                channel.txRollback()
                throw RuntimeException("트랜잭션 롤백 완료 ", e)
            } finally {
                try {
                    channel.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            null
        }
    }

}