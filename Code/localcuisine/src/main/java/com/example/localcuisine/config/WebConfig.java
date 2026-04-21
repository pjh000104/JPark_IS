package com.example.localcuisine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // //For development
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/api/**")
    //             .allowedOrigins("http://localhost:3000")
    //             .allowedMethods("GET", "POST", "PUT", "DELETE");
    // }
    
    // For Production
    @Override
    public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
    }

}