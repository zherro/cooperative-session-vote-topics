package br.com.southsystem.cooperative.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_sessao")
public class Session extends BaseEntityUUID {

    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private  String info;

    @ManyToOne
    @JoinColumn(name = "id_pauta")
    private Topic topic;

    @OneToMany(mappedBy = "session")
    private List<SessionVotes> votes;
}