package br.com.payments.spotify.infra.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfig
{
    private String accessToken;

    @PostConstruct
    public void init() {
        com.mercadopago.MercadoPagoConfig.setAccessToken("APP_USR-8592663389541603-042521-810679f6df8e8091bb09dc5a32a40937-3354452874");
    }
}
