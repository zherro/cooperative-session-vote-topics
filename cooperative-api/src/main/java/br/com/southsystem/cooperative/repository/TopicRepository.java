package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.Topic;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    Optional<Topic> getByUuid(String topic);
    Page<Topic> findAll(Pageable pageable);
}
