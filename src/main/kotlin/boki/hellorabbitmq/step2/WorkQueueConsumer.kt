package boki.hellorabbitmq.step2

import org.springframework.stereotype.Component

@Component
class WorkQueueConsumer {

    // version2
    fun workQueueTask(message: String) {
        val messageParts = message.split("\\|".toRegex())
        val originMessage = messageParts[0]
        // val duration = messageParts[1].toInt()
        val duration = messageParts[1].trim().toInt() // 프로그램 수정

        println("# Received: $originMessage(${duration}ms)")

        try {
            val seconds = duration / 1000
            for (i in 1..seconds) {
                Thread.sleep(1000) // 1초 대기
                print(".")
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        println("\n[X] Completed: $originMessage") // [X]: 소비됐다는 표시
    }

    // version1
    fun _workQueueTask(message: String) {
        // toRegex() 없으면 Error 발생하였음..
        val messageParts = message.split("\\|".toRegex())
        val originMessage = messageParts[0]
        val duration = messageParts[1].trim().toInt()

        println("# Received: $originMessage(${duration}ms)")

        try {
            println("now...sleep time $duration ms")
            Thread.sleep(duration.toLong())
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        println("# Completed: $originMessage")
    }

}