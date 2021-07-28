package br.com.southsystem.cooperative.controller;

import br.com.southsystem.cooperative.config.Cors;
import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.topic.TopicCreateDTO;
import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.dto.topic.TopicUpdateDTO;
import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.model.TopicQueryResult;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface TopicController extends Cors {
    Logger log();
    TopicService getService();
    SessionService getSessionService();
    ObjectMapper mapper();

    @GetMapping
    default PageResponse listTopic(RequestFilter filter) {
        log().info("m=listTopic, getting topics. page: {}, size: {}", filter.getPage(), filter.getSize());

        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = getService().list(pageable);
        var topics = result.stream()
                .map(TopicDTO::fromTopic)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/{uuid}")
    default ResponseEntity getTopic(@PathVariable String uuid) {
        log().info("m=getTopic, retrieve topic: {}", uuid);
        var topic = TopicDTO.fromTopic( getService().getByUuid(uuid) );
        var result = getSessionService().getLastSessionResultByTopic(uuid);
        topic.setResult(result);
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default void createTopic(@RequestBody TopicCreateDTO topic) {
        log().info("m=createTopic, creating topic");
        var topicEntity = mapper().convertValue(topic, Topic.class);
        topicEntity.setActive(true);
        topicEntity.setOpen(true);
        getService().create(topicEntity);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    default void updateTopic(@RequestParam("topicId") String topicId, @RequestBody TopicUpdateDTO topicUpdateDTO)
            throws JsonMappingException {
        log().info("m=updateTopic, updating topic: {}", topicId);
        var topic = getService().getByUuid(topicId);
        var topicDTO = mapper().convertValue(topicUpdateDTO, Topic.class);
        var topicMerged = mapper().updateValue(new Topic(), topicDTO);
        topicMerged.setId(topic.getId());
        topicMerged.setUuid(topic.getUuid());

        getService().update(topicMerged);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    default void closeTopic(@PathVariable("uuid") String uuid) {
        log().info("m=closeTopic, updating topic: {}", uuid);
        getService().close(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    default void deleteTopic(@PathVariable("uuid") String uuid) {
        log().info("m=deleteTopic, updating topic: {}", uuid);
        getService().remove(uuid);
    }

    default void getNextPointTime(TopicQueryResult qr, TopicDTO topic) {
        if(qr.getSession().getStartTime().isAfter(LocalDateTime.now())) {
            topic.setTimeLeft(false, qr.getSession().getStartTime());
        } else if(qr.getSession().getEndTime().isAfter(LocalDateTime.now())) {
            topic.setTimeLeft(true, qr.getSession().getEndTime());
        }
    }

}
