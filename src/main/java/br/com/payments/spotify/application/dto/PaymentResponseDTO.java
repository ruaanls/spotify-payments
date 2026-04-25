package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponseDTO
{
    private Long id;
    private String status;
    private String status_detail;
    private Double transaction_amount;
    private PointInteractionDTO point_of_interaction;
}
