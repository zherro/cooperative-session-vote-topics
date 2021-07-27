package br.com.southsystem.cooperative.facade;

import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static br.com.southsystem.cooperative.config.RabbitConfig.*;

@Component
public class SessionFacade  {
    private static final Logger log = LoggerFactory.getLogger(SessionFacade.class);

    private final RabbitTemplate rabbitTemplate;
    private final SessionService sessionService;
    private final TopicService topicService;

    public SessionFacade(RabbitTemplate rabbitTemplate, SessionService sessionService, TopicService topicService) {
        this.rabbitTemplate = rabbitTemplate;
        this.sessionService = sessionService;
        this.topicService = topicService;
    }

    public SessionService getSessionService() {
        return this.sessionService;
    }

    private Session createSession(String topicId, Session entity) {
        var topic = topicService.getByUuid(topicId);
        entity.setActive(true);
        entity.setTopic(topic);
        entity = sessionService.create(entity);
        return entity;
    }

    private void sendToQueue(SessionDTO dto) throws JsonProcessingException {
        var millisecondToCloseSession = LocalDateTime.now()
                .until(dto.getEndTime().plusSeconds(5), ChronoUnit.MILLIS);

        var msg = MessageBuilder
                .withBody(new ObjectMapper().writeValueAsBytes(dto))
                .setExpiration(String.valueOf(millisecondToCloseSession))
                .build();
        rabbitTemplate.send(SESSION_QUEUE_EXCHANGE_DELAYED, SESSION_QUEUE_DELAYED, msg);
    }

    public Session createAndPublishSession(String topicId, Session entity) {
        var session = createSession(topicId, entity);
        session.setTopic(null);
        try {
            log.info("m=createAndPublishSession, sending session to queue: {}",
                    session.getUuid());

            sendToQueue(SessionDTO.fromSession(session));
            log.info(
                    "m=createAndPublishSession, success to send session to queue, session: {}",
                    session.getUuid());
        } catch (JsonProcessingException ex) {
            log.error(
                    "m=createAndPublishSession, error to attempt publish session in queue, session: {}",
                    session.getUuid(),
                    ex);
        }
        return session;
    }

    public void handleSessionEnd(SessionDTO sessionDTO) {
        log.info("m=handleSessionEnd, retrieve session: {}", sessionDTO.getId());
        Session session = sessionService.getByUuid(sessionDTO.getId());

        // verifica se de fato a sessão encerrou por limite de tempo
        // se ainda não estiver encerrada reenvia para a delayed
        if(session.getEndTime().isAfter(LocalDateTime.now())) {
            try {
                log.info("m=handleSessionEnd, success to send session to queue, session: {}", session.getUuid());
                sendToQueue(sessionDTO);
            } catch (JsonProcessingException ex){
                log.error(
                    "m=handleSessionEnd, error to attempt publish session in queue, session: {}", session.getUuid(), ex);
            }
            return;
        }

        var result = sessionService.getLastSessionResultByTopic(session.getTopic().getUuid());
        var totalVotes =   result.stream().map(VoteSummary::getTotal).reduce(0L, Long::sum);
        if( totalVotes >= 3) {
            log.info("m=handleSessionEnd, closing topic: {}", session.getTopic().getUuid());
            var topic = session.getTopic();
            topic.setOpen(false);
            topicService.update(topic);
            log.info("m=handleSessionEnd, closed topic: {}", session.getTopic().getUuid());
        }
    }
}
