package com.example.demo.config;

import com.example.demo.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class FilterConfig {
    @Bean
    public WebFilter authFilter(AuthenticationFilter filter) {
        return filter;
    }
}
