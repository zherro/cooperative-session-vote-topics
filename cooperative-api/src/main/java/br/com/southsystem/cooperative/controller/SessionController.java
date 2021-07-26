package br.com.southsystem.cooperative.controller;

import br.com.southsystem.cooperative.config.Cors;
import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.session.RequestSessionFilter;
import br.com.southsystem.cooperative.dto.session.SessionCreateDTO;
import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.dto.session.SessionUpdateDTO;
import br.com.southsystem.cooperative.exceptions.AppMessages;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.TopicService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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

public interface SessionController extends Cors {
    Logger log();
    SessionService getService();
    TopicService getTopicService();
    ObjectMapper mapper();
    MessageSource messageSource();

    @GetMapping
    default PageResponse listSession(RequestSessionFilter filter) {
        log().info("m=listSession, getting sessions. page: {}, size: {}", filter.getPage(), filter.getSize());
        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = getService().list(pageable, filter);
        var topics = result.stream()
                .map(SessionDTO::fromSession)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/opened/{topic}")
    default ResponseEntity getOpenedSessionForTopic(@PathVariable String topic) {
        log().info("m=getOpenedSessionForTopic, retrieve opened session for topic: {}", topic);
        var session = getService().hasActiveSessionForTopic(topic);
        if(session == null) {
            MessageService.createBusinessException(messageSource(), AppMessages.MSG_EXCEPTION_NOT_HAVE_SESSION_OPENED_FOR_TOPIC);
        }
        return ResponseEntity.ok(SessionDTO.fromSession(session));
    }

    @GetMapping("/{uuid}")
    default ResponseEntity getSession(@PathVariable String uuid) {
        log().info("m=getSession, retrieve session: {}", uuid);
        var topic = SessionDTO.fromSession( getService().getByUuid(uuid) );
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default void openNewSession(@RequestBody SessionCreateDTO dto) {
        log().info("m=openNewSession, creating session for topic: {}", dto.getTopicId());
        var entity = mapper().convertValue(dto, Session.class);
        var topic = getTopicService().getByUuid(dto.getTopicId());
        entity.setActive(true);
        entity.setTopic(topic);
        entity = getService().create(entity);
        ResponseEntity.status(HttpStatus.CREATED)
                .body(SessionDTO.fromSession(entity));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    default void updateSession(@RequestParam("topicId") String sessionId, @RequestBody SessionUpdateDTO updateDTO)
            throws JsonMappingException {
        log().info("m=updateSession, updating session: {}", sessionId);
        var topic = getService().getByUuid(sessionId);
        var data = mapper().convertValue(updateDTO, Session.class);
        var topicMerged = mapper().updateValue(topic, data);

        getService().update(topicMerged);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    default void deleteSession(@PathVariable("uuid") String uuid) {
        log().info("m=deleteSession, updating session: {}", uuid);
        getService().remove(uuid);
    }



}
