package com.example.securenote.global.config;

import com.example.securenote.global.handler.RestAuthHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. /api/** 전용 체인: Stateless + CSRF off + 401 JSON
    @Bean @Order(1)
    SecurityFilterChain apiChain(HttpSecurity http, RestAuthHandlers restAuthHandlers) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(restAuthHandlers))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/health").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // 2. 웹 전용 체인: 세션 + 폼로그인 + CSRF on
    @Bean @Order(2)
    SecurityFilterChain webChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/error", "/css/**", "/js/**", "/images/**", "/health").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }
}
