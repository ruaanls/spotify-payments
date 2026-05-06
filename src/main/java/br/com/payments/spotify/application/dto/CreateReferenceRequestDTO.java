package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReferenceRequestDTO
{
    private PayerDTO payer;
    private String username;
}
