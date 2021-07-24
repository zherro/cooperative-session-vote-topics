package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.SessionVote;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionVoteRepository extends SpecRepository<SessionVote> {

    @Query(value = "FROM br.com.southsystem.cooperative.model.SessionVote v "
            + " INNER JOIN v.session s "
            + " INNER JOIN v.user u "
            + " WHERE s.uuid = :session "
            + " AND u.uuid = :user ")
    List<SessionVote> userHasVoted(String session, String user);
}
