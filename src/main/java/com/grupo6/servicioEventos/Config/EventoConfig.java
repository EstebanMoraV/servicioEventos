package com.grupo6.servicioEventos.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class EventoConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Servicio de Eventos API")
                        .version("1.0.0")
                        .description("API para gestionar eventos en la Red Social CGRI"));
    }

}
