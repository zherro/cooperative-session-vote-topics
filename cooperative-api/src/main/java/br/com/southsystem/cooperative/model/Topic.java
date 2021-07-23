package br.com.southsystem.cooperative.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_pauta")
public class Topic extends BaseEntityUUID {

    private String code;
    private String theme;
    private String description;
    private boolean open;

    @OneToMany(mappedBy = "topic")
    private List<Session> sessions;
}
