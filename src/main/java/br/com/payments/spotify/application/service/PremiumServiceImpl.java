package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.CallbackRequestDTO;
import br.com.payments.spotify.application.dto.EventPaymentDTO;
import br.com.payments.spotify.application.dto.PaymentDetailsDTO;
import br.com.payments.spotify.application.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PremiumServiceImpl
{
    void pedidoPix (EventPaymentDTO eventPayment);
    void callbackPayment (CallbackRequestDTO callbackRequestDTO);
    void consultarPagamento (Long id);

}
