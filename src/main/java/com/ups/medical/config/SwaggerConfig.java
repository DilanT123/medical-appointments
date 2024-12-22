package com.ups.medical.config;

/**
 *
 * @author Torres, Maddiekc
 */
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión de Citas Médicas API")
                        .version("1.0")
                        .description("API REST para la gestión de citas médicas, pacientes, médicos e historiales clínicos")
                        .termsOfService("http://ups.edu.ec/terms/")
                        .contact(new Contact()
                                .name("Grupo 01")
                                .email("dtorreso3@est.ups.edu.ec")
                                .url("https://github.com/DilanT123/medical-appointments"))
                        .license(new License()
                                .name("Uso Académico")
                                .url("http://ups.edu.ec/license")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo"),
                        new Server()
                                .url("https://medical-api.ups.edu.ec")
                                .description("Servidor de Producción")
                ));
    }

}
