package br.com.payments.spotify.application.dto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.*;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class PaymentRequestDTO
{
    private Double transaction_amount;
    private String description;
    private String payment_method_id;
    private String notification_url;
    private PayerDTO payer;

    public PaymentRequestDTO buildPayload(EventPaymentDTO eventPaymentDTO)
    {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey("my-secret-key")
//                .build()
//                .parseClaimsJws(eventPaymentDTO.getToken())
//                .getBody();
//        String email = claims.get("email", String.class);
//        String nomeCompleto = claims.get("name", String.class);
//        String[] partes = nomeCompleto.split(" ");
//        String firstName = partes[0];
//        String lastName = partes.length > 1 ? partes[partes.length - 1] : "N/A";


        return PaymentRequestDTO.builder()
                .transaction_amount(25.00)
                .description("Plano Premium - Spotify Stats")
                .payment_method_id("pix")
                //.notification_url("https://SEU_NGROK.ngrok-free.app/pagamentos/webhook")
                .payer(PayerDTO.builder()
                        .email("test_user_2068590855545158855@testuser.com")
                        .first_name("Test")
                        .last_name("Test")
                        .identification(IdentificationDto.builder()
                                .type("CPF")
                                .number("48639468800")
                                .build())
                        .build())
                .build();
    }
}
