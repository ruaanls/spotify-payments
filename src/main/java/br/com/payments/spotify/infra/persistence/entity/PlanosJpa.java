package br.com.payments.spotify.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "planos")
public class PlanosJpa
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome; // "PREMIUM_MENSAL"

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco; // 25.00
}
