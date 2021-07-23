package br.com.southsystem.cooperative.dto.session;

import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.model.Session;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionDTO {
    private final String id;
    private final TopicDTO topic;
    private final String name;

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int durationMinutes;
    private final String info;

    private final boolean active;

    public static SessionDTO fromSession(Session session) {
        return SessionDTO.builder()
                .id(session.getUuid())
                .active(session.isActive())
                .durationMinutes(session.getDurationMinutes())
                .endTime(session.getEndTime())
                .info(session.getInfo())
                .name(session.getName())
                .startTime(session.getStartTime())
                .topic( TopicDTO.fromTopic(session.getTopic()) )
                .build();
    }
}
