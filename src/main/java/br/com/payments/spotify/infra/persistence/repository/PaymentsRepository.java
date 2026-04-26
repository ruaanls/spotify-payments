package br.com.payments.spotify.infra.persistence.repository;

import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<PagamentoJpa, Long> {
    Optional<PagamentoJpa> findPagamentoJpaBymercadoPagoId(Long mercadoPagoId);
}
