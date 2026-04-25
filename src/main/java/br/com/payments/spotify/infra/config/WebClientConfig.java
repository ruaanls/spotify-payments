package br.com.payments.spotify.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfig
{
    @Bean
    public WebClient spotifyApiWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.mercadopago.com/v1/payments")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
