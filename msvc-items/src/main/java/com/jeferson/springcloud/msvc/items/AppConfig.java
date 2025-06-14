package com.jeferson.springcloud.msvc.items;

import java.time.Duration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

// Configuaracion parametros circuite breaker
@Configuration
public class AppConfig {

    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> customizerCircuiteBreakerFactory() {
        return (factory) -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig
                    .custom()
                    .slidingWindowSize(10)
                    .failureRateThreshold(30)
                    .waitDurationInOpenState(Duration.ofSeconds(10L))
                    .permittedNumberOfCallsInHalfOpenState(5)
                    .slowCallDurationThreshold(Duration.ofSeconds(2L))
                    .slowCallRateThreshold(50)
                    .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(4L)).build())
                    .build();
        });

    }
}
