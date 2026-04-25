package br.com.payments.spotify.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PayerDTO
{
    private String email;
    private String first_name;
    private String last_name;
    private IdentificationDto identification;
}
