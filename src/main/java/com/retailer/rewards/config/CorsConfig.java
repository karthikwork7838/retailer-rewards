package com.retailer.rewards.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) configuration for the rewards application.
 * 
 * <p>
 * This configuration class enables CORS for all REST endpoints in the application,
 * allowing requests from specified origins. The allowed origins are read from the
 * application properties using the {@code cross.origin} placeholder, which is resolved
 * by Spring's property resolver at runtime.
 * </p>
 * 
 * <p>
 * <b>Configuration Details:</b>
 * <ul>
 * <li><b>Allowed Origins:</b> Read from {@code cross.origin} property (default: "*")</li>
 * <li><b>Allowed Methods:</b> GET, POST, PUT, DELETE, OPTIONS</li>
 * <li><b>Allowed Headers:</b> All headers allowed ("*")</li>
 * <li><b>Path Pattern:</b> Applied to all API endpoints ("/**")</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Usage:</b>
 * Set the {@code cross.origin} property in application.properties to configure allowed origins.
 * Example: {@code cross.origin=http://localhost:3000,http://localhost:4200}
 * </p>
 * 
 * @author Karthik BK
 * @version 1.0
 * @since 1.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * CORS allowed origins read from application.properties via placeholder.
     * This value is injected by Spring's property resolver at application startup.
     */
    @Value("${cross.origin}")
    private String corsOrigins;

    /**
     * Registers CORS mappings for all API endpoints.
     * 
     * <p>
     * This method is called by Spring during application startup to configure CORS globally.
     * It allows requests from the configured origins to access all endpoints ("/**").
     * </p>
     * 
     * @param registry the {@code CorsRegistry} used to register CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
