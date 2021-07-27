package br.com.southsystem.cooperative.controller.v3;

import br.com.southsystem.cooperative.controller.UserController;
import br.com.southsystem.cooperative.controller.v2.UserControllerV2Impl;
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

@Api(value = "Cooperative API v3")
@RestController
@RequestMapping("/api/v3/users")
public class UserControllerV3Impl extends UserControllerV2Impl {

    private static final Logger log = LoggerFactory.getLogger(
            UserControllerV3Impl.class);

    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;

    public UserControllerV3Impl(ObjectMapper objectMapper, UserServiceImpl userService) {
        super(objectMapper, userService);
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public Logger log() {
        return log;
    }

}
