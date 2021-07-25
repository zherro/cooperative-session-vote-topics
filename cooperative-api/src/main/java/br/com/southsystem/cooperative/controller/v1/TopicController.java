package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.config.Cors;
import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.topic.TopicCreateDTO;
import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.dto.topic.TopicUpdateDTO;
import br.com.southsystem.cooperative.model.Topic;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/v1/pauta")
public class TopicController extends Cors {

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final SessionServiceImpl sessionService;

    public TopicController(ObjectMapper objectMapper, TopicService topicService, SessionServiceImpl sessionService) {
        this.objectMapper = objectMapper;
        this.topicService = topicService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public PageResponse listTopic(RequestFilter filter) {
        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = topicService.list(pageable);
        var topics = result.stream()
                .map(TopicDTO::fromTopic)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getTopic(@PathVariable String uuid) {
        var topic = TopicDTO.fromTopic( topicService.getByUuid(uuid) );
        var result = sessionService.getLastSessionResultByTopic(uuid);
        topic.setResult(result);
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTopic(@RequestBody TopicCreateDTO topic) {
        var topicEntity = objectMapper.convertValue(topic, Topic.class);
        topicEntity.setActive(true);
        topicEntity.setOpen(true);
        topicService.create(topicEntity);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTopic(@RequestParam("topicId") String topicId, @RequestBody TopicUpdateDTO topicUpdateDTO)
            throws JsonMappingException {
        var topic = topicService.getByUuid(topicId);
        var topicDTO = objectMapper.convertValue(topicUpdateDTO, Topic.class);
        var topicMerged = objectMapper.updateValue(topic, topicDTO);

        topicService.update(topicMerged);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void closeTopic(@PathVariable("uuid") String uuid) {
        topicService.close(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTopic(@PathVariable("uuid") String uuid) {
        topicService.remove(uuid);
    }

}
