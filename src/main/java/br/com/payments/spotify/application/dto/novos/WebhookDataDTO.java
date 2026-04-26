package br.com.payments.spotify.application.dto.novos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WebhookDataDTO
{
    @JsonProperty("id")
    private String id;
}
