package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS Configuration
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 Set allowed origin addresses
        corsConfiguration.addAllowedHeader("*"); // 2 Set allowed request headers
        corsConfiguration.addAllowedMethod("*"); // 3 Set allowed request methods
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 Apply CORS settings to all endpoints
        return new CorsFilter(source);
    }
}
