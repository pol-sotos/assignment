package com.assignment.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class OpenAPIConfig
{
    @Bean
    public OpenAPI myOpenAPI()
    {

        final var local = new Server();
        local.setUrl("http://localhost:8080");
        local.setDescription("Local development of CSV API");

        final Info info = new Info()
            .title("CSV API")
            .version("1.0")
            .description("Open API code generation and swagger ui of CSV API");

        return new OpenAPI().info(info).servers(List.of(local));
    }
}
