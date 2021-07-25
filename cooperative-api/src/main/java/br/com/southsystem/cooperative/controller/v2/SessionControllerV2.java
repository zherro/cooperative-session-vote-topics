package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v2/session")
public class SessionControllerV2
    extends br.com.southsystem.cooperative.controller.v1.SessionController {

    private final ObjectMapper objectMapper;
    private final SessionServiceImpl sessionService;
    private final TopicService topicService;

    public SessionControllerV2(ObjectMapper objectMapper, SessionServiceImpl sessionService, TopicService topicService) {
        super(objectMapper, sessionService, topicService);
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;
        this.topicService = topicService;
    }
}
