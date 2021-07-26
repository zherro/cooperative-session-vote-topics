package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.controller.UserController;
import br.com.southsystem.cooperative.dto.user.UserCreateDTO;
import br.com.southsystem.cooperative.service.UserService;
import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v2")
@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2Impl implements UserController {

    private static final Logger log = LoggerFactory.getLogger(UserControllerV2Impl.class);

    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;

    public UserControllerV2Impl(ObjectMapper objectMapper, UserServiceImpl userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public Logger log() {
        return log;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public ObjectMapper mapper() {
        return objectMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDTO dto) {
        log.info("m=createUser, creating user");
        if(dto.getPerson() != null && dto.getPerson().getDoc() != null) {
            dto.getPerson().setDoc(dto.getPerson().getDoc().replaceAll("[^0-9]*", ""));
        }
        UserController.super.createUser(dto);
    }

    @Override
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable("userId") String userId, @RequestBody UserCreateDTO dto) {
        log.info("m=updateUser, updating user: {}", userId);
        if(dto.getPerson() != null && dto.getPerson().getDoc() != null) {
            dto.getPerson().setDoc(dto.getPerson().getDoc().replaceAll("[^0-9]*", ""));
        }
        UserController.super.createUser(dto);
    }
}
