package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends SpecRepository<Session> {

    @Query("SELECT COUNT(s.id) > 0 FROM Session s "
            + " WHERE s.topic.uuid = :topicUuid "
            + " AND s.active = true AND s.endTime < now() ")
    boolean hasActiveSessionForTopic(String topicUuid);
}
