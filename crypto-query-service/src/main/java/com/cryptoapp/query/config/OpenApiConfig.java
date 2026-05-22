package com.cryptoapp.query.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cryptoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Crypto Query API")
                        .description("API REST para consultar criptomonedas en tiempo real. Datos de CoinGecko, actualizados cada 5 minutos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sara Cardenas")
                                .email("smcm981024@gmail.com")));
    }

}

