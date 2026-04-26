package br.com.payments.spotify.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig
{
    @Bean
    public WebClient spotifyApiWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.mercadopago.com")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
