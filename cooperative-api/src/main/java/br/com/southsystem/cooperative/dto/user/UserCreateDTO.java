package br.com.southsystem.cooperative.dto.user;

import br.com.southsystem.cooperative.model.Address;
import br.com.southsystem.cooperative.model.Person;
import br.com.southsystem.cooperative.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDTO {

    private String username;
    private String password;
    private PersonDTO person;

    public static User toUser(UserCreateDTO dto) {
        var user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        var person = new Person();
        person.setUser(user);
        person.setDoc(dto.getPerson().getDoc());
        person.setBirthday(dto.getPerson().getBirthday());
        person.setName(dto.getPerson().getName());

        var address = new Address();
        address.setCity(dto.getPerson().getCity());
        address.setComplement(dto.getPerson().getComplement());
        address.setCountry(dto.getPerson().getCountry());
        address.setNeighborhood(dto.getPerson().getNeighborhood());
        address.setNumber(dto.getPerson().getNumber());
        address.setPostalCode(dto.getPerson().getPostalCode());
        address.setStreet(dto.getPerson().getStreet());
        person.setAddress(address);

        user.setPerson(person);
        return user;
    }
}
