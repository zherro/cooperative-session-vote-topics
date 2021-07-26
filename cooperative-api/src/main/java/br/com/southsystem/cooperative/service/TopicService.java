package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.model.TopicQueryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicService extends SpecService<Topic> {
    void close(String uuid);
    Page<TopicQueryResult> listAllWithActiveSession(Pageable pageable);
}
