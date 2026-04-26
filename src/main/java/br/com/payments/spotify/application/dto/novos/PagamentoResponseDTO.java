package br.com.payments.spotify.application.dto.novos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoResponseDTO
{
    private String linkPagamento;   // getSandboxInitPoint()
}
