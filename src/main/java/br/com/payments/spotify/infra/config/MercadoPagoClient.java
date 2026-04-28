package br.com.payments.spotify.infra.config;

import br.com.payments.spotify.application.dto.IdentificationDto;
import br.com.payments.spotify.application.dto.PayerDTO;
import br.com.payments.spotify.application.dto.video.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.video.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.video.PaymentNotificationRequestDTO;
import br.com.payments.spotify.application.dto.video.PaymentNotificationResponseDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MercadoPagoClient
{

    @Value("${spring.api.v1.mercadopago-access-token}")
    private String accessToken;
    @Value("${spring.api.v1.mercadopago-notification-url}")
    private String notificationUrl;

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
            List<PreferenceItemRequest> items = input.getItens().stream()
                    .map(itemCapturado -> PreferenceItemRequest.builder()
                                .id(itemCapturado.getId())
                                .title(itemCapturado.getTitle())
                                .unitPrice(itemCapturado.getPrice())
                                .quantity(1)
                                .build())
                    .toList();
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
                    .autoReturn("approved")
                    .externalReference(UUID.randomUUID().toString())
                    .build();

            Preference preference = client.create(request);
            return new CreatePreferenceResponseDTO(preference.getId(), preference.getInitPoint());



        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }


    public PaymentNotificationResponseDTO getPaymentDetails(String paymentId)
    {
        try{
            PaymentClient client = new PaymentClient();
            Payment mpPayment = client.get(Long.valueOf(paymentId));

            String status = mpPayment.getStatus();
            String paymentMethod = mpPayment.getPaymentMethodId();
            PayerDTO payer = null;
            if(mpPayment.getPayer() != null && mpPayment.getPayer().getIdentification() != null)
            {
                payer = PayerDTO.builder()
                        .first_name(mpPayment.getPayer().getFirstName())
                        .identification(IdentificationDto.builder()
                                .number(mpPayment.getPayer().getIdentification().getNumber())
                                .type(mpPayment.getPayer().getIdentification().getType())
                                .build())
                        .build();
            }

            return new PaymentNotificationResponseDTO(
                    status,paymentMethod, payer
            );
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

}
