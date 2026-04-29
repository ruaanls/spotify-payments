package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;


public interface PremiumServiceImpl
{

    CreatePreferenceResponseDTO createPreference (CreateReferenceRequestDTO input);
    void processPaymentNotificiation(CallbackNotificationDTO input, String username);


}
