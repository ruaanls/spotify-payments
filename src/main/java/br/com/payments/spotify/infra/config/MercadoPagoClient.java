package br.com.payments.spotify.infra.config;

import br.com.payments.spotify.application.dto.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.EventResponseDTO;
import br.com.payments.spotify.application.exception.PaymentData;
import br.com.payments.spotify.application.exception.PaymentProcess;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class MercadoPagoClient
{

    @Value("${spring.api.v1.mercadopago-access-token}")
    private String accessToken;

    @PostConstruct
    public void init()
    {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public CreatePreferenceResponseDTO createPreference(CreateReferenceRequestDTO input)
    {
        try
        {
            PreferenceClient client = new PreferenceClient();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(PreferenceItemRequest.builder()
                    .id("1")
                    .title("Spotify Analytics - Premium")
                    .unitPrice(BigDecimal.valueOf(20.00))
                    .quantity(1)
                    .build());
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(input.getPayer().getFirst_name())
                    .identification(IdentificationRequest.builder()
                            .number(input.getPayer().getIdentification().getNumber())
                            .type(input.getPayer().getIdentification().getType())
                            .build())
                    .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.mercadopago.com.br")
                    .failure("https://www.google.com")
                    .pending("https://www.google.com")
                    .build();

            PreferenceRequest request = PreferenceRequest.builder()
                    .items(items)
                    .payer(payer)
                    .backUrls(backUrls)
                    .notificationUrl("https://curry-crushable-subdivide.ngrok-free.dev/payments/callback")
                    .autoReturn("approved")
                    .externalReference(input.getUsername())
                    .build();

            Preference preference = client.create(request);
            return new CreatePreferenceResponseDTO(preference.getInitPoint());



        } catch (MPException | MPApiException e) {
            throw new PaymentProcess();
        }
    }


    public EventResponseDTO getPaymentDetails(String paymentId, String username)
    {
        try{
            PaymentClient client = new PaymentClient();
            Payment mpPayment = client.get(Long.valueOf(paymentId));

            if(mpPayment.getPayer() != null && mpPayment.getPayer().getIdentification() != null)
            {
                return EventResponseDTO.builder()
                        .payment_method(mpPayment.getPaymentMethodId())
                        .event("payment_status")
                        .status(mpPayment.getStatus())
                        .username(mpPayment.getExternalReference())
                        .build();
            }
            return null;

        } catch (MPException | MPApiException e) {
            throw new PaymentData();
        }
    }

}
