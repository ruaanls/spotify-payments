package br.com.payments.spotify.infra.persistence.adapter;

import br.com.payments.spotify.domain.repository.PaymentsRepoServiceImpl;
import br.com.payments.spotify.infra.persistence.entity.PagamentoJpa;
import br.com.payments.spotify.infra.persistence.repository.PaymentsRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class PaymentsRepositoryAdapter implements PaymentsRepoServiceImpl
{

    private final PaymentsRepository paymentsRepository;

    public PaymentsRepositoryAdapter(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public void createPayment(PagamentoJpa pagamentoJpa) {
        paymentsRepository.save(pagamentoJpa);
    }

    @Override
    public PagamentoJpa getPayment(Long id) {
        Optional<PagamentoJpa> pagamentoJpa = paymentsRepository.findPagamentoJpaBymercadoPagoId(id);
        if(pagamentoJpa.isPresent())
        {
            return pagamentoJpa.get();
        }
        else
        {
            throw new RuntimeException();
        }
    }
}
