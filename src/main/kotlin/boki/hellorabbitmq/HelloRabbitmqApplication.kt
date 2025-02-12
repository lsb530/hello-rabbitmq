package boki.hellorabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

const val prefix = "boki.hellorabbitmq"
const val step1 = "${prefix}.step1" // 단순 메시지 전송
const val step2 = "${prefix}.step2" // WorkQueue(Competing Consumers Pattern), 컨슈머간 작업분배(Round Robin), 재처리
const val step3 = "${prefix}.step3" // Pub/Sub 모델을 이용한 실시간 알림 구현(WebSocket, STOMP)
const val step4 = "${prefix}.step4" // 뉴스타입별 구독 알림 시스템
const val step5 = "${prefix}.step5" // Routing 모델-Direct Exchange Log 수집
const val step6 = "${prefix}.step6" // Routing 모델-Topic Exchange Log 수집
const val step7 = "${prefix}.step7" // DLX와 DLQ를 이용한 재처리 (manual 모드 세팅)
const val step8 = "${prefix}.step8" // RetryTemplate을 사용한 DeadLetter 재처리

@SpringBootApplication(scanBasePackages = [step8])
class HelloRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<HelloRabbitmqApplication>(*args)
}
