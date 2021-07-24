package br.com.southsystem.cooperative.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_users")
public class User extends BaseEntityUUID {

    private String username;
    private  String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Person person;

}
