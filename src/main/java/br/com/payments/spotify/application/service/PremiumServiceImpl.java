package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.CallbackRequestDTO;
import br.com.payments.spotify.application.dto.EventPaymentDTO;
import br.com.payments.spotify.application.dto.PaymentDetailsDTO;
import br.com.payments.spotify.application.dto.PaymentResponseDTO;
import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.novos.PagamentoResponseDTO;
import br.com.payments.spotify.application.dto.video.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.video.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.video.PaymentNotificationRequestDTO;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.stereotype.Service;


public interface PremiumServiceImpl
{

    CreatePreferenceResponseDTO createPreference (CreateReferenceRequestDTO input);
    void processPaymentNotificiation(CallbackNotificationDTO input);


}
