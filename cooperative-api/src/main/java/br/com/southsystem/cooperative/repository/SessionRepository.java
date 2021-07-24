package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.Session;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends SpecRepository<Session> {

    @Query(value = "SELECT s FROM br.com.southsystem.cooperative.model.Session s  "
            + " INNER JOIN s.topic t "
            + " WHERE t.uuid = :topicUuid "
            + " AND s.active = true AND s.endTime < :atDate ORDER BY s.endTime")
    List<Session> hasActiveSessionForTopic(String topicUuid, LocalDateTime atDate);
}
