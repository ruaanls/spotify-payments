package br.com.payments.spotify.application.usecases;

import br.com.payments.spotify.application.dto.*;
import br.com.payments.spotify.application.dto.novos.CallbackNotificationDTO;
import br.com.payments.spotify.application.dto.novos.PagamentoResponseDTO;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import br.com.payments.spotify.application.service.RedisServiceImpl;
import br.com.payments.spotify.domain.model.StatusPagamento;
import br.com.payments.spotify.domain.repository.PaymentsRepoServiceImpl;
import br.com.payments.spotify.infra.config.WebClientConfig;
import br.com.payments.spotify.infra.persistence.entity.AssinaturaJpa;
import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import br.com.payments.spotify.infra.persistence.entity.PlanosJpa;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PremiumService implements PremiumServiceImpl
{

    private final RedisServiceImpl redisService;
    private final String ACCESS_TOKEN = "APP_USR-8592663389541603-042521-810679f6df8e8091bb09dc5a32a40937-3354452874";



    public PremiumService(RedisServiceImpl redisService ) {
        this.redisService = redisService;
        // Configura o token globalmente para o SDK
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    }

    @Override
    public PagamentoResponseDTO pedidoPix(String usuarioId) throws MPException, MPApiException {
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Spotify Analytics - Premium")
                .quantity(1)
                .unitPrice(new BigDecimal("20.00"))
                .currencyId("BRL")
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(List.of(item))
                .externalReference(usuarioId)
                .notificationUrl("https://curry-crushable-subdivide.ngrok-free.dev/payments/callback")
                .paymentMethods(
                        PreferencePaymentMethodsRequest.builder()
                                .excludedPaymentTypes(List.of(
                                        PreferencePaymentTypeRequest.builder().id("ticket").build(),        // boleto
                                        PreferencePaymentTypeRequest.builder().id("debit_card").build()     // débito
                                ))
                                .build()
                )
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request);
        PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO();
        pagamentoResponseDTO.setLinkPagamento(preference.getSandboxInitPoint());
        return pagamentoResponseDTO;
    }

    @Override
    public void callbackPayment(CallbackNotificationDTO body) throws MPException, MPApiException {
        String topic = body.getTopic();
        String resource = body.getResource();

        if (topic == null || resource == null) return;

        System.out.println("Recebendo notificação: Topic=" + topic + " | Resource=" + resource);

        try {
            if ("payment".equals(topic)) {
                // Extrai o ID do resource (seja URL ou apenas o número)
                String id = resource.replaceAll("\\D+", "");
                processarPagamentoDireto(Long.valueOf(id));
            }
            else if ("merchant_order".equals(topic)) {
                String id = resource.replaceAll("\\D+", "");
                processarOrdem(Long.valueOf(id));
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar callback: " + e.getMessage());
        }
    }

    private void processarPagamentoDireto(Long paymentId) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);

        if ("approved".equals(payment.getStatus())) {
            finalizarEntrega(String.valueOf(payment.getId()), payment.getExternalReference());
        }
    }

    private void processarOrdem(Long orderId) throws MPException, MPApiException {
        try {
            MerchantOrderClient client = new MerchantOrderClient();
            MerchantOrder order = client.get(orderId);

            System.out.println("DEBUG: Ordem recuperada. ID: " + order.getId() + " Status: " + order.getOrderStatus());

            if (order.getPayments() != null && !order.getPayments().isEmpty()) {
                // Usando for tradicional para não confundir o debugger e ver o erro real
                for (int i = 0; i < order.getPayments().size(); i++) {
                    var p = order.getPayments().get(i);

                    System.out.println("DEBUG: Analisando pagamento index " + i + " - Status: " + p.getStatus());

                    if ("approved".equals(p.getStatus())) {
                        String paymentId = String.valueOf(p.getId());
                        String usuarioId = order.getExternalReference(); // Na sua imagem é "ruansz_"

                        System.out.println("DEBUG: Chamando finalizarEntrega para: " + usuarioId);
                        finalizarEntrega(paymentId, usuarioId);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ ERRO CRÍTICO NO PROCESSAR ORDEM:");
            e.printStackTrace(); // <--- ISSO AQUI vai te dizer a linha exata e o motivo do erro no console
        }
    }

    private void finalizarEntrega(String paymentId, String usuarioId) {
        // Sua lógica do Redis para evitar duplicidade
        String jaProcessado = this.redisService.getTokenRedis(paymentId, "pagamentoProcessado");
        if (jaProcessado == null) {
            this.redisService.saveTokenRedis(paymentId, paymentId, "pagamentoProcessado");
            System.out.println("✅ Pagamento APROVADO! Liberando para o usuário: " + usuarioId);
        }
    }




}
