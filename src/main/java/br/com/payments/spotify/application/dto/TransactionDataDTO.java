package br.com.payments.spotify.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDataDTO
{
    private String qr_code;
    private String qr_code_base64;
    private String ticket_url;
}
