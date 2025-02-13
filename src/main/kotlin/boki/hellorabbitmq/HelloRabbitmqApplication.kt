package boki.hellorabbitmq

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

const val prefix = "boki.hellorabbitmq"
const val step1 = "${prefix}.step1" // 단순 메시지 전송
const val step2 = "${prefix}.step2" // WorkQueue(Competing Consumers Pattern), 컨슈머간 작업분배(Round Robin), 재처리
const val step3 = "${prefix}.step3" // Pub/Sub 모델을 이용한 실시간 알림 구현(WebSocket, STOMP)
const val step4 = "${prefix}.step4" // 뉴스타입별 구독 알림 시스템
const val step5 = "${prefix}.step5" // Routing 모델-Direct Exchange Log 수집
const val step6 = "${prefix}.step6" // Routing 모델-Topic Exchange Log 수집
const val step7 = "${prefix}.step7" // DLX와 DLQ를 이용한 재처리 (manual 모드 세팅)
const val step8_1 = "${prefix}.step8_1" // RetryTemplate을 사용한 DeadLetter 재처리
const val step8_2 = "${prefix}.step8_2" // Retry(yaml) 설정을 통한 DeadLetter 재처리
const val step9 = "${prefix}.step9" // DB 연동 메시지큐의 트랜잭션 처리 실습(일종의 동기방식)
const val step10 = "${prefix}.step10" // Publisher-confirms 사용하여 트랜잭션 처리(비동기)

@EntityScan(basePackages = [step10])
@EnableJpaRepositories(basePackages = [step10])
@SpringBootApplication(scanBasePackages = [step10])
class HelloRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<HelloRabbitmqApplication>(*args)
}
