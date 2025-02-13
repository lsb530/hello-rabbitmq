package boki.hellorabbitmq.step10

import com.rabbitmq.client.Channel
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class MessageConsumer(
    private val stockRepository: StockRepository
) {

    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME], containerFactory = "rabbitListenerContainerFactory")
    fun receiveMessage(
        stock: StockEntity,
        @Header(AmqpHeaders.DELIVERY_TAG) deliveryTag: Long,
        channel: Channel
    ) {
        try {
            println("[Consumer] $stock")
            Thread.sleep(200) // 데이터가 저장되기를 기다림(조회시 데이터가 있도록)
            stockRepository.findByIdOrNull(stock.id())?.let { stockEntity ->
                stockEntity.invokeUpdated()
                stockRepository.save(stockEntity)
                println("[Save Entity Consumer] $stockEntity")
            } ?: {
                throw RuntimeException("Stock not found")
            }
            channel.basicAck(deliveryTag, false)
        } catch (e: Exception) {
            println("[Consumer Error] ${e.message}")
            try {
                channel.basicNack(deliveryTag, false, false)
            } catch (ex: IOException) {
                println("[Consumer send nack] ${ex.message}")
                // throw new RuntimeException(ex);
            }
        }
    }
}