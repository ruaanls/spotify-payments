package br.com.payments.spotify.application.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IdentificationDto
{
    private String type;
    private String number;
}
