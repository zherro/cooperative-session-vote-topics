package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.repository.TopicRepository;
import br.com.southsystem.cooperative.service.TopicService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final MessageSource messageSource;

    public TopicServiceImpl(TopicRepository topicRepository, MessageSource messageSource) {
        this.topicRepository = topicRepository;
        this.messageSource = messageSource;
    }

    @Override
    public SpecRepository<Topic> getRepository() {
        return topicRepository;
    }

    @Override
    public RuntimeException notFoundException(String key) {
        return new ResourceNotFoundException(messageSource, Topic.class.getSimpleName() , key);
    }
}
