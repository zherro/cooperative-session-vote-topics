package br.com.southsystem.cooperative.dto.topic;

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
}
