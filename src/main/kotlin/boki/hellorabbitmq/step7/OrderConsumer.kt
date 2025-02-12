package boki.hellorabbitmq.step7

import com.rabbitmq.client.Channel
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class OrderConsumer {

    companion object {
        private const val MAX_RETRIES = 3   // 총 시도 제한 수
        private var retryCount = 0          // 재시도 횟수
    }

    @RabbitListener(queues = [RabbitMQConfig.ORDER_COMPLETED_QUEUE], containerFactory = "rabbitListenerContainerFactory")
    fun processOrder(message: String, channel: Channel, @Header("amqp_deliveryTag") tag: Long) {
        try {
            // 실패 유발
            if ("fail".equals(message, ignoreCase = true)) {
                if (retryCount < MAX_RETRIES) {
                    System.err.println("#### Fail & Retry = $retryCount")
                    retryCount++
                    throw RuntimeException(message)
                } else {
                    System.err.println("#### 최대 횟수 초과, DLQ로 이동")
                    retryCount = 0
                    channel.basicNack(tag, false, false)
                    return
                }
            }
            // 성공 처리
            println("# 성공: $message")
            channel.basicAck(tag, false)
            retryCount = 0
        } catch (e: Exception) {
            System.err.println("# error 발생: ${e.message}")
            try {
                // 실패 시 basicReject 재처리 전송
                channel.basicReject(tag, true)
            } catch (e1: IOException) {
                System.err.println("# fail & reject message: ${e1.message}")
            }
        }
    }

}