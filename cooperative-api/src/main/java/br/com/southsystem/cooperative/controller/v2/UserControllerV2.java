package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v2/user")
public class UserControllerV2
    extends br.com.southsystem.cooperative.controller.v1.UserController {

    private final ObjectMapper objectMapper;
    private final UserServiceImpl userService;

    public UserControllerV2(ObjectMapper objectMapper, UserServiceImpl userService) {
        super(objectMapper, userService);
        this.objectMapper = objectMapper;
        this.userService = userService;
    }
}
