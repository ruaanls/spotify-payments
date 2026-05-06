package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.EventResponseDTO;


public interface PremiumServiceImpl
{

    CreatePreferenceResponseDTO createPreference (CreateReferenceRequestDTO input);
    EventResponseDTO processPaymentNotificiation(CallbackNotificationDTO input, String username);


}
