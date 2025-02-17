package com.aselsan.VendingMachine.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Vending Machine API",
        version = "1.0",
        description = "API for managing vending machines",
        contact = @Contact(
            name = "Admin",
            email = "sevimmuharrem@gmail.com"
        )
    ),
    servers = {
        @Server(
            description = "Local Environment",
            url = "http://localhost:8080"
        )
    }
)
@SecurityScheme(
    name = "basicAuth",
    description = "Basic Authentication",
    scheme = "basic",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
} 