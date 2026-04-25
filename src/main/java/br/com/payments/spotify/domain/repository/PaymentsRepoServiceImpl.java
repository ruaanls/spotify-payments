package br.com.payments.spotify.domain.repository;

import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;

public interface PaymentsRepoServiceImpl
{
    void createPayment(PagamentoJpa pagamentoJpa);
    PagamentoJpa getPayment(Long id);

}
