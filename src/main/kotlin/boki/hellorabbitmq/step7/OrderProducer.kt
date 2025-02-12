package boki.hellorabbitmq.step7

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class OrderProducer(
    private val rabbitTemplate: RabbitTemplate
) {

    fun sendShipping(message: String) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_EXCHANGE,
            "order.completed.shipping",
            message
        )
        println("[주문 완료. 배송 지시 메시지 생성: $message]")
    }

}