package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventPaymentDTO
{
    private String event;
    private String cpf;
    private String token;

}
