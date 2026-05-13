package br.com.payments.spotify.application.usecases;

import br.com.payments.spotify.application.dto.EventResponseDTO;
import br.com.payments.spotify.application.exception.PaymentData;
import br.com.payments.spotify.application.service.EventServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sns.core.SnsTemplate;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements EventServiceImpl
{
    private final SnsTemplate snsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.aws.sns.topic-arn}")
    private String topicArn;


    @Override
    public void publicarEvento(EventResponseDTO evento) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(evento);
        try {
            snsTemplate.send(
                    topicArn,
                    MessageBuilder
                            .withPayload(payload)
                            .build()
            );
        } catch (Exception e) {
            throw new PaymentData();
        }
    }
}
