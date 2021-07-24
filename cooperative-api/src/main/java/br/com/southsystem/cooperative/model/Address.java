package br.com.southsystem.cooperative.model;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String country;
    private String city;
    private String neighborhood;
    private String street;
    private String postalCode;
    private String number;
    private String complement;
}
