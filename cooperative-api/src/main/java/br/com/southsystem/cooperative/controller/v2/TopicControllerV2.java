package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v2/pauta")
public class TopicControllerV2
    extends br.com.southsystem.cooperative.controller.v1.TopicController {

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final SessionServiceImpl sessionService;

    public TopicControllerV2(ObjectMapper objectMapper, TopicService topicService, SessionServiceImpl sessionService) {
        super(objectMapper, topicService, sessionService);
        this.objectMapper = objectMapper;
        this.topicService = topicService;
        this.sessionService = sessionService;
    }
}
