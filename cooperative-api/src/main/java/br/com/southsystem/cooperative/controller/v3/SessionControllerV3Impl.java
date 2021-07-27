package br.com.southsystem.cooperative.controller.v3;

import br.com.southsystem.cooperative.controller.SessionController;
import br.com.southsystem.cooperative.controller.v1.SessionControllerV1Impl;
import br.com.southsystem.cooperative.dto.session.SessionCreateDTO;
import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.facade.SessionFacade;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v3")
@RestController
@RequestMapping("/api/v3/sessao")
public class SessionControllerV3Impl implements SessionController {

    private static final Logger log = LoggerFactory.getLogger(SessionControllerV1Impl.class);


    private final ObjectMapper objectMapper;
    private final SessionFacade sessionFacade;
    private final MessageSource messageSource;

    public SessionControllerV3Impl(
            ObjectMapper objectMapper,
            SessionFacade sessionFacade,
            MessageSource messageSource
    ) {
        this.objectMapper = objectMapper;
        this.sessionFacade = sessionFacade;
        this.messageSource = messageSource;
    }

    @Override
    public Logger log() {
        return log;
    }

    @Override
    public SessionService getService() {
        return sessionFacade.getSessionService();
    }

    @Override
    public TopicService getTopicService() {
        return null;
    }

    @Override
    public ObjectMapper mapper() {
        return objectMapper;
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void openNewSession(SessionCreateDTO dto) {
        log().info("m=openNewSession, creating session for topic: {}", dto.getTopicId());
        var entity = mapper().convertValue(dto, Session.class);
        sessionFacade.createAndPublishSession(dto.getTopicId(), entity);
    }
}
