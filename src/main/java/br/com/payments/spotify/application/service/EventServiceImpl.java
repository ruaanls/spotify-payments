package br.com.payments.spotify.application.service;

import br.com.payments.spotify.application.dto.EventResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface EventServiceImpl
{
    void publicarEvento(EventResponseDTO evento) throws JsonProcessingException;
}
