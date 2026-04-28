package br.com.payments.spotify.application.dto.video;

import br.com.payments.spotify.application.dto.IdentificationDto;
import br.com.payments.spotify.application.dto.PayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentNotificationResponseDTO
{
    private String status;
    private String method;
    private PayerDTO payerDTO;
}
