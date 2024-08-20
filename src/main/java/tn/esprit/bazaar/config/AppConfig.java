// src/main/java/tn/esprit/bazaar/config/AppConfig.java
package tn.esprit.bazaar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Additional configuration if needed
        return objectMapper;
    }
}