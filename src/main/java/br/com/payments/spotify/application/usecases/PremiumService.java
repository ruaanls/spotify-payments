package br.com.payments.spotify.application.usecases;

import br.com.payments.spotify.application.dto.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.EventResponseDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import br.com.payments.spotify.infra.config.MercadoPagoClient;
import org.springframework.stereotype.Service;


@Service
public class PremiumService implements PremiumServiceImpl
{
    private final MercadoPagoClient mercadoPagoClient;

    public PremiumService(MercadoPagoClient mercadoPagoClient) {
        this.mercadoPagoClient = mercadoPagoClient;
    }

    @Override
    public CreatePreferenceResponseDTO createPreference(CreateReferenceRequestDTO input) {
        return mercadoPagoClient.createPreference(input);
    }

    @Override
    public EventResponseDTO processPaymentNotificiation(CallbackNotificationDTO input, String username) {
        String paymentId = input.getData().getId();
        return mercadoPagoClient.getPaymentDetails(paymentId, username);
    }

}
