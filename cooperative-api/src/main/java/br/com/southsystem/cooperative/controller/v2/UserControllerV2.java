package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.dto.user.UserCreateDTO;
import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDTO dto) {
        if(dto.getPerson() != null && dto.getPerson().getDoc() != null) {
            dto.getPerson().setDoc(dto.getPerson().getDoc().replaceAll("[^0-9]*", ""));
        }
        super.createUser(dto);
    }

    @Override
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable("userId") String userId, @RequestBody UserCreateDTO dto) {
        if(dto.getPerson() != null && dto.getPerson().getDoc() != null) {
            dto.getPerson().setDoc(dto.getPerson().getDoc().replaceAll("[^0-9]*", ""));
        }
        super.createUser(dto);
    }
}
