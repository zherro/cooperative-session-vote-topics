package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.controller.SessionController;
import br.com.southsystem.cooperative.controller.v1.SessionControllerV1Impl;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v2")
@RestController
@RequestMapping("/api/v2/sessao")
public class SessionControllerV2Impl implements SessionController {

    private static final Logger log = LoggerFactory.getLogger(SessionControllerV1Impl.class);


    private final ObjectMapper objectMapper;
    private final SessionServiceImpl sessionService;
    private final TopicService topicService;
    private final MessageSource messageSource;

    public SessionControllerV2Impl(ObjectMapper objectMapper, SessionServiceImpl sessionService, TopicService topicService,
            MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;
        this.topicService = topicService;
        this.messageSource = messageSource;
    }

    @Override
    public Logger log() {
        return log;
    }

    @Override
    public SessionService getService() {
        return sessionService;
    }

    @Override
    public TopicService getTopicService() {
        return topicService;
    }

    @Override
    public ObjectMapper mapper() {
        return objectMapper;
    }

    @Override
    public MessageSource messageSource() {
        return messageSource;
    }
}
