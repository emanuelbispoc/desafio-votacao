package com.assembleia.app.votacao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class OpenApiConfig {
    private final OpenAPI openAPI;
    private final ObjectMapper objectMapper;

    @Autowired
    public OpenApiConfig(ObjectMapper objectMapper, ResourceLoader resourceLoader) throws IOException {
        this.objectMapper = objectMapper;
        this.openAPI = carregarJson(resourceLoader.getResource("classpath:/doc-schema.json").getInputStream());
    }

    @Bean
    public OpenAPI openAPI() {
        return openAPI;
    }

    private OpenAPI carregarJson(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, OpenAPI.class);
    }
}