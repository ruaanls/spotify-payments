package br.com.payments.spotify.application.dto.video;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePreferenceResponseDTO
{
    private String preferenceId;
    private String redirectUrl;
}
