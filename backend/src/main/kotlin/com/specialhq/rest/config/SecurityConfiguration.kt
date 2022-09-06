package com.specialhq.rest.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        return http.build()
    }
}