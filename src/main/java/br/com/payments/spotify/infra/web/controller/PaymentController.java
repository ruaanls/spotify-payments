package br.com.payments.spotify.infra.web.controller;

import br.com.payments.spotify.application.dto.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.EventResponseDTO;
import br.com.payments.spotify.application.service.EventServiceImpl;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final EventServiceImpl eventService;


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
    public ResponseEntity callback (@RequestBody CallbackNotificationDTO callbackRequestDTO) throws JsonProcessingException {
        EventResponseDTO eventResponseDTO = this.premiumService.processPaymentNotificiation(callbackRequestDTO, this.username);
        if(eventResponseDTO != null)
        {
            this.eventService.publicarEvento(eventResponseDTO);
        }
        return ResponseEntity.ok().build();
    }
}
