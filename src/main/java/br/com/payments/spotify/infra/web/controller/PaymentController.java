package br.com.payments.spotify.infra.web.controller;

import br.com.payments.spotify.application.dto.CallbackRequestDTO;
import br.com.payments.spotify.application.dto.EventPaymentDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController
{
    private final PremiumServiceImpl premiumService;

    @PostMapping()
    public ResponseEntity pedidoPix (@RequestBody EventPaymentDTO eventPaymentDTO)
    {
        this.premiumService.pedidoPix(eventPaymentDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/callback")
    public ResponseEntity callback (@RequestBody CallbackRequestDTO callbackRequestDTO)
    {
        this.premiumService.callbackPayment(callbackRequestDTO);
        return ResponseEntity.ok().build();
    }
}
