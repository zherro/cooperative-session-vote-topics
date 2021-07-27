package br.com.southsystem.cooperative.controller.v3;

import br.com.southsystem.cooperative.controller.v2.TopicControllerV2Impl;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v3")
@RestController
@RequestMapping("/api/v3/pauta")
public class TopicControllerV3Impl extends TopicControllerV2Impl {

    private static final Logger log = LoggerFactory.getLogger(
            TopicControllerV3Impl.class);

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final SessionServiceImpl sessionService;

    public TopicControllerV3Impl(ObjectMapper objectMapper, TopicService topicService, SessionServiceImpl sessionService) {
        super(objectMapper, topicService, sessionService);
        this.objectMapper = objectMapper;
        this.topicService = topicService;
        this.sessionService = sessionService;
    }

    @Override
    public Logger log() {
        return log;
    }

}
