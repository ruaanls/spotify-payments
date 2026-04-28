package br.com.payments.spotify.application.dto.video;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotificationRequestDTO
{
    private String resourceType;
    private String resourceId;
}
