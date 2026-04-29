package br.com.payments.spotify.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CallbackNotificationDTO
{
    @JsonProperty("user_id")
    private Long id;                // ID interno da notificação

    @JsonProperty("type")
    private String type;            // "payment" — tipo do evento

    @JsonProperty("action")
    private String action;          // "payment.created" ou "payment.updated"

    @JsonProperty("live_mode")
    private Boolean liveMode;       // false no sandbox, true em produção

    @JsonProperty("data")
    private WebhookDataDTO data;

    @JsonProperty("api_version")
    private String api_version;

    @JsonProperty("date_created")
    private String date_created;
}
