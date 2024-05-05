package ubb.mppbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply CORS configuration to specific path patterns
            .allowedOrigins("http://localhost:3000") // Allow requests from this origin
            .allowedMethods("GET", "POST") // Allow only specified HTTP methods
            .allowedHeaders("Content-Type", "Authorization"); // Allow only specified headers
    }
}
