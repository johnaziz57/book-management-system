package com.john.bookmanagementsystem.configuration.security.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
annotation class AdminAuthorization