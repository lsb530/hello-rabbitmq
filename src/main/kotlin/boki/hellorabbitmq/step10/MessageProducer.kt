package boki.hellorabbitmq.step10

import org.springframework.amqp.rabbit.connection.CorrelationData
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Component
class MessageProducer(
    private val rabbitTemplate: RabbitTemplate,
    private val stockRepository: StockRepository
) {

    @Transactional
    fun sendMessage(stockEntity: StockEntity, testCase: Boolean) {
        stockEntity.updateProcessed(false)
        stockEntity.invokeCreated()
        val entity = stockRepository.save(stockEntity)

        println("[producer entity]: $entity")

        if (stockEntity.userId().isBlank()) {
            throw RuntimeException("User id is required")
        }

        try {
            // 메시지를 rabbitmq 에 전송
            val correlationData = CorrelationData(entity.id().toString())
            rabbitTemplate.convertAndSend(
                if (testCase) "nonExistentExchange" else RabbitMQConfig.EXCHANGE_NAME,
                if (testCase) "invalidRoutingKey" else RabbitMQConfig.ROUTING_KEY,
                entity,
                correlationData
            )

            if (correlationData.future.get(5, TimeUnit.SECONDS).isAck) {
                println("[producer correlationData] 성공: $entity")
                entity.updateProcessed(true)
                stockRepository.save(entity)
            } else {
                throw RuntimeException("# confirm 실패 - 롤백")
            }
        } catch (e: Exception) {
            println("[producer exception fail]: $e")
            throw RuntimeException(e)
        }
    }

}