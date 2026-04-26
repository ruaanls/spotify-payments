package br.com.payments.spotify.infra.web.controller;

import br.com.payments.spotify.application.dto.CallbackRequestDTO;
import br.com.payments.spotify.application.dto.EventPaymentDTO;
import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.novos.PagamentoRequestDTO;
import br.com.payments.spotify.application.dto.novos.PagamentoResponseDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController
{
    private final PremiumServiceImpl premiumService;

    @PostMapping()
    public ResponseEntity<PagamentoResponseDTO> pedidoPix (@RequestBody PagamentoRequestDTO pagamentoRequestDTO) throws MPException, MPApiException {
        return new ResponseEntity<>(this.premiumService.pedidoPix(pagamentoRequestDTO.getUsuarioId()), HttpStatus.CREATED);
    }

    @PostMapping("/callback")
    public ResponseEntity callback (@RequestBody CallbackNotificationDTO callbackRequestDTO) throws MPException, MPApiException {
        this.premiumService.callbackPayment(callbackRequestDTO);
        return ResponseEntity.ok().build();
    }
}
