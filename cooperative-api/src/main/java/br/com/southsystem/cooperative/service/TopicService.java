package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService {

    Page<Topic> list(Pageable pageable);
    Topic create(Topic topic);
    Topic getByUuid(String topic);
    Topic update(Topic topic);
    void remove(String id);

}
