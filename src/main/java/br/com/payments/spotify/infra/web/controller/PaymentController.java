package br.com.payments.spotify.infra.web.controller;

import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.video.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.video.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
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



    @PostMapping()
    public ResponseEntity<CreatePreferenceResponseDTO> createPreference(@RequestBody CreateReferenceRequestDTO request)
    {
        try{
            CreatePreferenceResponseDTO fullResponse = premiumService.createPreference(request);
            CreatePreferenceResponseDTO responseDTO = new CreatePreferenceResponseDTO(
                    fullResponse.getPreferenceId(), fullResponse.getRedirectUrl());
            return ResponseEntity.ok(responseDTO);
        }
        catch(Exception e)
        {
            log.error("Error creating payment preference for userId {}: {}", request.getUserId(), e.getMessage(), e);
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @PostMapping("/callback")
    public ResponseEntity callback (@RequestBody CallbackNotificationDTO callbackRequestDTO) throws MPException, MPApiException {
         this.premiumService.processPaymentNotificiation(callbackRequestDTO);
        return ResponseEntity.ok().build();
    }
}
