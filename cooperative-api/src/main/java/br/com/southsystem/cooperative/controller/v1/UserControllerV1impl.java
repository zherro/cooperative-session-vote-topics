package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.controller.UserController;
import br.com.southsystem.cooperative.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1impl implements UserController {

    private static final Logger log = LoggerFactory.getLogger(UserControllerV1impl.class);

    private final ObjectMapper objectMapper;

    @Qualifier("userServiceV1")
    private final UserService userService;

    public UserControllerV1impl(ObjectMapper objectMapper, UserService userService) {
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
}
