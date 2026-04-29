package br.com.payments.spotify.infra.web.controller;

import br.com.payments.spotify.application.dto.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController
{
    private final PremiumServiceImpl premiumService;
    private String username;


    @PostMapping()
    public ResponseEntity<CreatePreferenceResponseDTO> createPreference(@RequestBody CreateReferenceRequestDTO request)
    {
        CreatePreferenceResponseDTO fullResponse = premiumService.createPreference(request);
        this.username = request.getUsername();
        CreatePreferenceResponseDTO responseDTO = new CreatePreferenceResponseDTO(
                fullResponse.getRedirectUrl());
        return ResponseEntity.ok(responseDTO);

    }


    @PostMapping("/callback")
    public ResponseEntity callback (@RequestBody CallbackNotificationDTO callbackRequestDTO){
         this.premiumService.processPaymentNotificiation(callbackRequestDTO, this.username);
         this.username = null;
        return ResponseEntity.ok().build();
    }
}
