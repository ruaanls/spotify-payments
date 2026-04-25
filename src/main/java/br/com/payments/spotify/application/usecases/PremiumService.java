package br.com.payments.spotify.application.usecases;

import br.com.payments.spotify.application.dto.*;
import br.com.payments.spotify.application.service.PremiumServiceImpl;
import br.com.payments.spotify.application.service.RedisServiceImpl;
import br.com.payments.spotify.domain.model.StatusPagamento;
import br.com.payments.spotify.domain.repository.PaymentsRepoServiceImpl;
import br.com.payments.spotify.infra.config.WebClientConfig;
import br.com.payments.spotify.infra.persistence.entity.AssinaturaJpa;
import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import br.com.payments.spotify.infra.persistence.entity.PlanosJpa;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
public class PremiumService implements PremiumServiceImpl
{

    private final RedisServiceImpl redisService;
    private PaymentRequestDTO paymentRequestDTO;
    private final WebClient webClient;
    private final PaymentsRepoServiceImpl paymentsRepoService;


    public PremiumService(RedisServiceImpl redisService, @Qualifier("spotifyApiWebClient") WebClient webClient, PaymentsRepoServiceImpl paymentsRepoService) {
        this.redisService = redisService;
        this.webClient = webClient;
        this.paymentsRepoService = paymentsRepoService;
    }

    @Override
    public void pedidoPix(EventPaymentDTO eventPayment) {
        String idempotencia = this.redisService.getTokenRedis(eventPayment.getCpf(), "idempotencia");
        try
        {
           PaymentRequestDTO paymentRequest = this.paymentRequestDTO.buildPayload(eventPayment);
           PaymentResponseDTO paymentResponseDTO =  webClient.post()
                   .uri("/v1/payments")
                   .header("Authorization", "Bearer "+ "APP_USR-8681227517453961-042221-38bbc15bfa654f7aed0be6b7f0671f05-3354452874")
                   .header("X-Idempotency-Key", idempotencia)
                   .contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(paymentRequest)
                   .retrieve()
                   .bodyToMono(PaymentResponseDTO.class)
                   .block();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey("my-secret-key")
                    .build()
                    .parseClaimsJws(eventPayment.getToken())
                    .getBody();
            String username = claims.get("username", String.class);

            PlanosJpa planosJpa = new PlanosJpa();
            planosJpa.setNome("PREMIUM MENSAL");
            planosJpa.setPreco(25.00);

            AssinaturaJpa assinaturaJpa = new AssinaturaJpa();
            assinaturaJpa.setUsuarioId(username);
            assinaturaJpa.setAtivo(true);
            assinaturaJpa.setPlano(planosJpa);

           PagamentoJpa pagamentoJpa = new PagamentoJpa();
            pagamentoJpa.setMercadoPagoId(paymentResponseDTO.getId());
            pagamentoJpa.setAssinatura(assinaturaJpa);
            pagamentoJpa.setValor(BigDecimal.valueOf(paymentResponseDTO.getTransaction_amount()));
            pagamentoJpa.setPixCopiaECola(paymentResponseDTO.getPoint_of_interaction().getTransaction_data().getQr_code());
            pagamentoJpa.setTicketUrl(paymentResponseDTO.getPoint_of_interaction().getTransaction_data().getTicket_url());
            pagamentoJpa.setChaveIdempotencia(idempotencia);
            pagamentoJpa.setStatus(StatusPagamento.PENDENTE);
            pagamentoJpa.setCriadoEm(LocalDateTime.now());
            paymentsRepoService.createPayment(pagamentoJpa);



           EventPaymentStatusDTO eventPaymentStatusDTO = new EventPaymentStatusDTO();
           eventPaymentStatusDTO.setEvent("Status-pagamento");
           eventPaymentStatusDTO.setId_payment(paymentResponseDTO.getId());
           eventPaymentStatusDTO.setStatus(paymentResponseDTO.getStatus());

           //Salvar evento

        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao criar pagamento PIX: ", e);
        }
    }

    @Override
    public void callbackPayment(CallbackRequestDTO callbackRequestDTO) {
        consultarPagamento(callbackRequestDTO.getId());
    }

    @Override
    public void consultarPagamento(Long id)
    {
        try{
            PaymentDetailsDTO paymentDetails = webClient.get()
                    .uri("/v1/payments/"+id)
                    .header("Authorization","Bearer "+"APP_USR-8681227517453961-042221-38bbc15bfa654f7aed0be6b7f0671f05-3354452874")
                    .retrieve()
                    .bodyToMono(PaymentDetailsDTO.class)
                    .block();

            if(paymentDetails.getStatus().equals("approved"))
            {
                PagamentoJpa pagamentoJpa =  this.paymentsRepoService.getPayment(id);
                pagamentoJpa.setStatus(StatusPagamento.APROVADO);
                this.paymentsRepoService.createPayment(pagamentoJpa);

                EventPaymentStatusDTO eventPaymentStatusDTO = new EventPaymentStatusDTO();
                eventPaymentStatusDTO.setEvent("Status-pagamento");
                eventPaymentStatusDTO.setId_payment(paymentDetails.getId());
                eventPaymentStatusDTO.setStatus(paymentDetails.getStatus());
                //Salva novo evento

            }
        }
        catch (Exception e )
        {
            throw new RuntimeException();
        }

    }
}
