package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.dto.pageable.PageResponse;
import br.com.southsystem.cooperative.dto.pageable.PageableRequest;
import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import br.com.southsystem.cooperative.dto.topic.TopicCreateDTO;
import br.com.southsystem.cooperative.dto.topic.TopicUpdateDTO;
import br.com.southsystem.cooperative.dto.user.UserCreateDTO;
import br.com.southsystem.cooperative.dto.user.UserDTO;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.service.UserService;
import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;

    public UserController(ObjectMapper objectMapper, UserServiceImpl userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @GetMapping
    public PageResponse listUsers(RequestFilter filter) {
        var pageable = new PageableRequest(filter.getPage(), filter.getSize(), "createdAt", 10).build();
        var result = userService.list(pageable);
        var topics = result.stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
        return new PageResponse<>(result, topics);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getUser(@PathVariable String uuid) {
        var topic = UserDTO.fromUser( userService.getByUuid(uuid) );
        return ResponseEntity.ok().body(topic);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDTO dto) {
        var entity = UserCreateDTO.toUser(dto);
        userService.create(entity);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateTopic(@PathVariable("userId") String userId, @RequestBody TopicUpdateDTO dto)
            throws JsonMappingException {
        var user = userService.getByUuid(userId);
        var userDto = objectMapper.convertValue(dto, User.class);
        var merged = objectMapper.updateValue(user, userDto);

        userService.update(merged);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("uuid") String uuid) {
        userService.remove(uuid);
    }

}
