package br.com.southsystem.cooperative.dto.topic;

import lombok.Data;

@Data
public class TopicUpdateDTO {
    private String theme;
    private String description;
    private boolean open;
}
