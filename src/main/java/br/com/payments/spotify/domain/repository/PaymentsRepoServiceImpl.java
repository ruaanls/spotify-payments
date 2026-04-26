package br.com.payments.spotify.domain.repository;

import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepoServiceImpl
{
    void createPayment(PagamentoJpa pagamentoJpa);
    PagamentoJpa getPayment(Long id);

}
