package org.springbootdemo.ecommerceserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/public/**").permitAll() // Allow access to public endpoints
                                .anyRequest().authenticated() // Require authentication for other endpoints
                )
                .httpBasic(AbstractHttpConfigurer::disable); // Disable HTTP Basic authentication
        return http.build();
    }
}