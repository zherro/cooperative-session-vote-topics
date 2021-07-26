package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.model.TopicQueryResult;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends SpecRepository<Topic> {

    @Query(value = "SELECT new br.com.southsystem.cooperative.model.TopicQueryResult ( "
            + "     t, s "
            + " ) FROM br.com.southsystem.cooperative.model.Topic t "
                    + " LEFT JOIN t.sessions s  "
              + "         ON  s.topic.id = t.id "
              + "         AND s.active = true "
              + "         AND s.endTime > :atDate" )
    Page<TopicQueryResult> findAll(LocalDateTime atDate, Pageable pageable);
}
