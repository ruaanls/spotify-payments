package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventPaymentStatusDTO
{
    private String event;
    private Long id_payment;
    private String status;
    private Double valor;
}
