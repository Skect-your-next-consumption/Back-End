package com.hana.common.util;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Value("${openai.secret}")
    private String apiKey;

    @Bean
    public OpenAiService getOpenAiService() {
        return new OpenAiService(apiKey);
    }
}
