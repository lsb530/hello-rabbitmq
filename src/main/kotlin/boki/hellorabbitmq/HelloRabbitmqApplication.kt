package boki.hellorabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

const val prefix = "boki.hellorabbitmq"
const val step1 = "${prefix}.step1"
const val step2 = "${prefix}.step2"
const val step3 = "${prefix}.step3"

@SpringBootApplication(scanBasePackages = [step3])
class HelloRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<HelloRabbitmqApplication>(*args)
}
