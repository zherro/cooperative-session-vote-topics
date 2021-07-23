package br.com.southsystem.cooperative.model;

import br.com.southsystem.cooperative.model.types.Vote;
import br.com.southsystem.cooperative.model.types.VoteTypeConverter;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_sessao_votos")
public class SessionVotes  extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_sessao")
    private Session session;

    @Convert(converter = VoteTypeConverter.class)
    private Vote vote;
}
