package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.controller.TopicController;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/pauta")
public class TopicControllerV1Impl implements TopicController {

    private static final Logger log = LoggerFactory.getLogger(TopicControllerV1Impl.class);

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final SessionServiceImpl sessionService;

    public TopicControllerV1Impl(ObjectMapper objectMapper, TopicService topicService, SessionServiceImpl sessionService) {
        this.objectMapper = objectMapper;
        this.topicService = topicService;
        this.sessionService = sessionService;
    }

    @Override
    public Logger log() {
        return log;
    }

    @Override
    public TopicService getService() {
        return topicService;
    }

    @Override
    public SessionService getSessionService() {
        return sessionService;
    }

    @Override
    public ObjectMapper mapper() {
        return objectMapper;
    }
}
