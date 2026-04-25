package br.com.payments.spotify.infra.persistence.repository;

import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<PagamentoJpa, Long> {
    Optional<PagamentoJpa> findPagamentoJpaBymercadoPagoId(Long mercadoPagoId);
}
