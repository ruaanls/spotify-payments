package br.com.payments.spotify.domain.model;

import lombok.Getter;

@Getter
public enum StatusPagamento
{
    APROVADO("ACSC", "Liquidação confirmada e crédito realizado"),

    // ACSP = AcceptedSettlementInProcess
    PENDENTE("ACSP", "Pagamento aguardando processamento ou liquidação"),

    // RJCT = Rejected
    RECUSADO("RJCT", "Pagamento rejeitado pela instituição financeira");

    private final String siglaBcb;
    private final String descricao;

    StatusPagamento(String siglaBcb, String descricao) {
        this.siglaBcb = siglaBcb;
        this.descricao = descricao;
    }
}
