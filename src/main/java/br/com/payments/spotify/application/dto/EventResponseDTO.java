package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponseDTO
{
    private String event;
    private String status;
    private String payment_method;
    private String username;
}
