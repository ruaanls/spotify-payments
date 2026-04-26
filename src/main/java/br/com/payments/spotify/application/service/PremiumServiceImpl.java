package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.CallbackRequestDTO;
import br.com.payments.spotify.application.dto.EventPaymentDTO;
import br.com.payments.spotify.application.dto.PaymentDetailsDTO;
import br.com.payments.spotify.application.dto.PaymentResponseDTO;
import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.novos.PagamentoResponseDTO;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.stereotype.Service;


public interface PremiumServiceImpl
{
    PagamentoResponseDTO pedidoPix (String usuarioId) throws MPException, MPApiException;
    void callbackPayment (CallbackNotificationDTO callbackNotificationDTO) throws MPException, MPApiException;


}
