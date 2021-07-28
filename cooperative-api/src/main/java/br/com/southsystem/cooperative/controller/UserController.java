package br.com.southsystem.cooperative.controller;

import br.com.southsystem.cooperative.config.Cors;
import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.user.UserCreateDTO;
import br.com.southsystem.cooperative.dto.user.UserDTO;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserController extends Cors {
    Logger log();
    UserService getUserService();
    ObjectMapper mapper();

    @GetMapping
    default PageResponse listUsers(RequestFilter filter) {
        log().info("m=listUsers, getting users. page: {}, size: {}", filter.getPage(), filter.getSize());

        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = getUserService().list(pageable);
        var topics = result.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/{uuid}")
    default ResponseEntity getUser(@PathVariable String uuid) {
        log().info("m=getUser, retrieve user: {}", uuid);
        var topic = UserDTO.fromUser( getUserService().getByUuid(uuid) );
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default void createUser(@RequestBody UserCreateDTO dto) {
        log().info("m=createUser, creating user");
        var entity = UserCreateDTO.toUser(dto);
        getUserService().create(entity);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    default void updateUser(@PathVariable("userId") String userId, @RequestBody UserCreateDTO dto)
            throws JsonMappingException {
        log().info("m=updateUser, updating user: {}", userId);
        var user = getUserService().getByUuid(userId);
        var userDto = mapper().convertValue(dto, User.class);
        var merged = mapper().updateValue(new User(), userDto);
        merged.setId(user.getId());
        merged.setUuid(user.getUuid());

        getUserService().update(merged);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    default void deleteUser(@PathVariable("uuid") String uuid) {
        log().info("m=deleteUser, updating user: {}", uuid);
        getUserService().remove(uuid);
    }

}
