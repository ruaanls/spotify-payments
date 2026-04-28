package br.com.payments.spotify.application.dto.novos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CallbackNotificationDTO
{
    @JsonProperty("id")
    private Long id;                // ID interno da notificação

    @JsonProperty("type")
    private String type;            // "payment" — tipo do evento

    @JsonProperty("act ion")
    private String action;          // "payment.created" ou "payment.updated"

    @JsonProperty("live_mode")
    private Boolean liveMode;       // false no sandbox, true em produção

    @JsonProperty("data")
    private WebhookDataDTO data;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("resource")
    private String resource;
}
