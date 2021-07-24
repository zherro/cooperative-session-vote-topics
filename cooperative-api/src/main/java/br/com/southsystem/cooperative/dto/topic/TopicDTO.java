package br.com.southsystem.cooperative.dto.topic;

import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopicDTO {
    private String id;
    private String code;
    private String theme;
    private String description;
    private boolean active;
    private boolean open;

    private List<VoteSummary> result = new ArrayList<>();

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
