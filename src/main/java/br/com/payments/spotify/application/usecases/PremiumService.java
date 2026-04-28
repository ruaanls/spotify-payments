package br.com.payments.spotify.application.usecases;


import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.video.CreatePreferenceResponseDTO;
import br.com.payments.spotify.application.dto.video.CreateReferenceRequestDTO;
import br.com.payments.spotify.application.dto.video.PaymentNotificationRequestDTO;
import br.com.payments.spotify.application.dto.video.PaymentNotificationResponseDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import br.com.payments.spotify.application.service.RedisServiceImpl;
import br.com.payments.spotify.infra.config.MercadoPagoClient;
import com.mercadopago.resources.payment.Payment;
import org.springframework.stereotype.Service;


@Service
public class PremiumService implements PremiumServiceImpl
{
    private final RedisServiceImpl redisService;
    private final MercadoPagoClient mercadoPagoClient;

    public PremiumService(RedisServiceImpl redisService, MercadoPagoClient mercadoPagoClient) {
        this.redisService = redisService;
        this.mercadoPagoClient = mercadoPagoClient;
    }

    @Override
    public CreatePreferenceResponseDTO createPreference(CreateReferenceRequestDTO input) {
        try {
            return mercadoPagoClient.createPreference(input);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating the payment preference.", e);

        }
    }

    @Override
    public void processPaymentNotificiation(CallbackNotificationDTO input) {
        String paymentId = input.getData().getId();
        try{
            PaymentNotificationResponseDTO responseDTO = mercadoPagoClient.getPaymentDetails(paymentId);

        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
    }

}
