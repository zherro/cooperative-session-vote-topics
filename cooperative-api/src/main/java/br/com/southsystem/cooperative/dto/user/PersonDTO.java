package br.com.southsystem.cooperative.dto.user;

import br.com.southsystem.cooperative.model.Person;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String name;
    private String doc;
    private LocalDate birthday;

    private String country;
    private String city;
    private String neighborhood;
    private String street;
    private String postalCode;
    private String number;
    private String complement;

    public static PersonDTO fromPerson(Person person) {
        var dto = PersonDTO.builder()
                .name(person.getName())
                .doc(person.getDoc())
                .birthday(person.getBirthday());
        if(person.getAddress() != null) {
            dto.country(person.getAddress().getCountry())
                    .city(person.getAddress().getCity())
                    .neighborhood(person.getAddress().getNeighborhood())
                    .street(person.getAddress().getStreet())
                    .postalCode(person.getAddress().getPostalCode())
                    .number(person.getAddress().getNumber())
                    .complement(person.getAddress().getComplement());
        }
        return dto.build();
    }
}
