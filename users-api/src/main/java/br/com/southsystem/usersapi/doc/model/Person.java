package br.com.southsystem.usersapi.doc.model;

import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_persons")
public class Person extends BaseEntity {

    private String name;
    private String doc;
    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
}
