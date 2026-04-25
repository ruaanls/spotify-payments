package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CallbackRequestDTO
{
    private Long id;
    private boolean live_mode;
    private String type;
    private String payment_updated;
    private String date_created;
    private String api_version;
    private DataDTO data;
}
