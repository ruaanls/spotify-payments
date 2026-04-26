package br.com.payments.spotify.infra.persistence.entity;

import br.com.payments.spotify.domain.model.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagamentos",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_chave_idempotencia",
                columnNames = "chave_idempotencia"
        )
)
public class PagamentoJpa
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O ID que você envia para evitar duplicidade
    @Column(name = "chave_idempotencia", nullable = false, unique = true, length = 36)
    private String chaveIdempotencia;

    @Column(name = "mercado_pago_id", unique = true)
    private Long mercadoPagoId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinatura_id", nullable = false)
    private AssinaturaJpa assinatura;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,  columnDefinition = "VARCHAR(20)")
    private StatusPagamento status; // PENDENTE, APROVADO, RECUSADO, CANCELADO

    // Dados para o cliente conseguir pagar se sair da tela
    @Column(name = "pix_copia_e_cola", columnDefinition = "TEXT")
    private String pixCopiaECola;

    @Column(name = "ticket_url", length = 500)
    private String ticketUrl;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;


    @PrePersist
    private void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
