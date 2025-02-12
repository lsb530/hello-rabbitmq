package boki.hellorabbitmq.step8

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@Configuration
class RetryConfig {

    @Bean
    fun retryTemplate2(): RetryTemplate =
        RetryTemplate().apply {
            // 재시도 정책 설정: 최대 3번 시도
            setRetryPolicy(SimpleRetryPolicy().apply { maxAttempts = 3 })

            // 백오프 정책 설정: 재시도 간격 1초
            setBackOffPolicy(FixedBackOffPolicy().apply { backOffPeriod = 1000L })
        }

}