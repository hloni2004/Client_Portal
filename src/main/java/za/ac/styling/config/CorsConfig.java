package za.ac.styling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);
        
        // Allow frontend origins (including different ports)
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173",
            "http://127.0.0.1:5173",
            "http://localhost:8081",
            "http://127.0.0.1:8081",
            "http://localhost:3000",
            "http://127.0.0.1:3000"
        ));
        
        // Allow all headers
        config.setAllowedHeaders(Arrays.asList("*"));
        
        // Allow all HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Expose headers that the frontend can read
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Max age for preflight requests
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/api/**", config);
        
        return new CorsFilter(source);
    }
}