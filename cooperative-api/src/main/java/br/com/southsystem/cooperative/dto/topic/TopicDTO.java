package br.com.southsystem.cooperative.dto.topic;

import br.com.southsystem.cooperative.model.Topic;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopicDTO {
    private String id;
    private String code;
    private String theme;
    private String description;
    private boolean active;
    private boolean open;

    public static TopicDTO fromTopic(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getUuid())
                .active(topic.isActive())
                .code(topic.getCode())
                .description(topic.getDescription())
                .open(topic.isOpen())
                .theme(topic.getTheme())
                .build();
    }
}
