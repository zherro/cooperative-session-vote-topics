package br.com.southsystem.cooperative.dto.user;

import br.com.southsystem.cooperative.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {

    private String id;
    private String username;
    private PersonDTO person;

    public static UserDTO fromUser(User user) {
        return UserDTO.builder()
                .id(user.getUuid())
                .username(user.getUsername())
                .person(user.getPerson() == null ? null : PersonDTO.fromPerson(user.getPerson()))
                .build();
    }
}
