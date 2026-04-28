package br.com.payments.spotify.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayerDTO
{
    private String first_name;
    private IdentificationDto identification;
}
