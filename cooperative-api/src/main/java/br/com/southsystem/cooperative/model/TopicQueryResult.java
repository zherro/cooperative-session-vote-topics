package br.com.southsystem.cooperative.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicQueryResult {
    private Topic topic;
    private Session session;
}
