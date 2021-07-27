package br.com.southsystem.cooperative.consumer;

import br.com.southsystem.cooperative.config.RabbitConfig;
import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.facade.SessionFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ManagerSessionConsumer {

    private static final Logger log = LoggerFactory.getLogger(ManagerSessionConsumer.class);

    private final ObjectMapper objectMapper;
    private final SessionFacade sessionFacade;

    public ManagerSessionConsumer(ObjectMapper objectMapper, SessionFacade sessionFacade) {
        this.objectMapper = objectMapper;
        this.sessionFacade = sessionFacade;
    }

    @RabbitListener(queues = RabbitConfig.SESSION_QUEUE, containerFactory = "sessionManagerFactory")
    public void sessionConsumer(final Message message) {
        log.info("m=sessionConsumer, start validating message");
        var sessionDTO = validateMessage(message);
        sessionFacade.handleSessionEnd(sessionDTO);
    }

    private SessionDTO validateMessage(Message message) {
        try {
            return objectMapper.readValue(message.getBody(), SessionDTO.class);
        } catch (Exception e) {
            log.error("m=validateMessage, invalid message format!");
            throw new BusinessException("invalid message format!");
        }
    }
}
