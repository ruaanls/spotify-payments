package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetailsDTO
{
    private Long id;
    private String status;
    private String status_detail;
    private Long transaction_amount;
    private String payment_method_id;
    private String date_approved;
}
