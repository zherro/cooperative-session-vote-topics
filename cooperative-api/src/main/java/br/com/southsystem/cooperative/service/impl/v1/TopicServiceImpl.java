package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.repository.TopicRepository;
import br.com.southsystem.cooperative.service.TopicService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;
    private MessageSource messageSource;

    public TopicServiceImpl(TopicRepository topicRepository, MessageSource messageSource) {
        this.topicRepository = topicRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Page<Topic> list(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    @Override
    public Topic create(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic getByUuid(String topic) {
        var notFoundMsg = new ResourceNotFoundException(messageSource, "topic" , topic);

        return topicRepository.getByUuid(topic)
                .orElseThrow(() -> notFoundMsg);
    }

    @Override
    public Topic update(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void remove(String topicCode) {
        var topic = getByUuid(topicCode);
        topic.setActive(false);
        topicRepository.save(topic);
    }
}
