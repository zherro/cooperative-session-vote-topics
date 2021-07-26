package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.controller.TopicController;
import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.model.TopicQueryResult;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v2")
@RestController
@RequestMapping("/api/v2/pauta")
public class TopicControllerV2Impl implements TopicController {

    private static final Logger log = LoggerFactory.getLogger(TopicControllerV2Impl.class);

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final SessionServiceImpl sessionService;

    public TopicControllerV2Impl(ObjectMapper objectMapper, TopicService topicService, SessionServiceImpl sessionService) {
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

    @Override
    @GetMapping
    public PageResponse listTopic(RequestFilter filter) {
        log().info("m=listTopic, getting topics. page: {}, size: {}", filter.getPage(), filter.getSize());

        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = getService().listAllWithActiveSession(pageable);
        var topics = result.stream()
                .map(qr -> {
                    var topic = TopicDTO.fromTopic(qr.getTopic());
                    topic.setSession(SessionDTO.fromSession(qr.getSession()));
                    if(qr.getSession() != null) {
                        getNextPointTime(qr, topic);
                    }
                    topic.setResult(sessionService.getLastSessionResultByTopic(topic.getId()));
                    return topic;
                })
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }


}
