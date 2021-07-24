package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.Session;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends SpecRepository<Session> {

    @Query(value = "SELECT s FROM br.com.southsystem.cooperative.model.Session s  "
            + " INNER JOIN s.topic t "
            + " WHERE t.uuid = :topicUuid "
            + " AND s.active = true AND s.endTime > :atDate ORDER BY s.endTime DESC")
    List<Session> hasActiveSessionForTopic(String topicUuid, LocalDateTime atDate);

    @Query(value = "FROM br.com.southsystem.cooperative.model.Session s  "
            + " WHERE true = :getAll "
            + " OR (false = :activeFilter AND s.active = :activeFilter ) "
            + " OR (true = :activeFilter AND s.active = :activeFilter AND s.endTime > :atDate )"
            + " ORDER BY s.endTime ")
    Page<Session> findAll(boolean getAll, boolean activeFilter, LocalDateTime atDate, Pageable pageable);
}
