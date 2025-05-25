package org.example.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Value("${springdoc.api-docs.path}")
    public String apiDocsRoute;

    @Override
    public void addCorsMappings(CorsRegistry reg) {
        reg
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}