package br.com.southsystem.cooperative.dto.user;

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

}
