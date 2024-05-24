package com.john.bookmanagementsystem.configuration.datetime

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock


@Configuration
class DateTime {
    // TODO create test with clock progress
    @Bean
    fun clock(): Clock {
        return Clock.systemDefaultZone()
    }
}