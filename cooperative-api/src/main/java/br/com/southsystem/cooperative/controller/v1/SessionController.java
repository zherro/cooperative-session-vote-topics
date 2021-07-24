package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.session.RequestSessionFilter;
import br.com.southsystem.cooperative.dto.session.SessionCreateDTO;
import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.dto.session.SessionUpdateDTO;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final ObjectMapper objectMapper;
    private final SessionServiceImpl sessionService;
    private final TopicService topicService;

    public SessionController(ObjectMapper objectMapper, SessionServiceImpl sessionService, TopicService topicService) {
        this.objectMapper = objectMapper;
        this.sessionService = sessionService;
        this.topicService = topicService;
    }

    @GetMapping
    public PageResponse listTopic(RequestSessionFilter filter) {
        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = sessionService.list(pageable, filter);
        var topics = result.stream()
                .map(SessionDTO::fromSession)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/opened/{topic}")
    public ResponseEntity getOpenedSessionForTopic(@PathVariable String topic) {
        var session = sessionService.hasActiveSessionForTopic(topic);
        if(session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SessionDTO.fromSession(session));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getSession(@PathVariable String uuid) {
        var topic = SessionDTO.fromSession( sessionService.getByUuid(uuid) );
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void openNewSession(@RequestBody SessionCreateDTO dto) {
        var entity = objectMapper.convertValue(dto, Session.class);
        var topic = topicService.getByUuid(dto.getTopicId());
        entity.setActive(true);
        entity.setTopic(topic);
        entity = sessionService.create(entity);
        ResponseEntity.status(HttpStatus.CREATED)
                .body(SessionDTO.fromSession(entity));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateSession(@RequestParam("topicId") String topicId, @RequestBody SessionUpdateDTO updateDTO)
            throws JsonMappingException {
        var topic = sessionService.getByUuid(topicId);
        var data = objectMapper.convertValue(updateDTO, Session.class);
        var topicMerged = objectMapper.updateValue(topic, data);

        sessionService.update(topicMerged);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSession(@PathVariable("uuid") String uuid) {
        sessionService.remove(uuid);
    }



}
