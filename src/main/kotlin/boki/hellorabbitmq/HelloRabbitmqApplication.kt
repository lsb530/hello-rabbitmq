package boki.hellorabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloRabbitmqApplication

fun main(args: Array<String>) {
    runApplication<HelloRabbitmqApplication>(*args)
}
