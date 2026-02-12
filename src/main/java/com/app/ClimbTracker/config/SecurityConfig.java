package com.app.ClimbTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 1. Allow POST/PUT without complex tokens (Good for APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // 2. Allow Swagger for everyone
                        .anyRequest().authenticated() // 3. Lock everything else
                )
                .httpBasic(Customizer.withDefaults()); // 4. Use "Basic Auth" (The Padlock) instead of a Login Page

        return http.build();
    }
}