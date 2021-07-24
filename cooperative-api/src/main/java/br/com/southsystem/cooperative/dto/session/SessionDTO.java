package br.com.southsystem.cooperative.dto.session;

import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final List<VoteSummary> result;

    public static SessionDTO fromSession(Session session) {
        return SessionDTO.builder()
                .id(session.getUuid())
                .active(session.isActive() && session.getEndTime().isAfter(LocalDateTime.now()))
                .durationMinutes(session.getDurationMinutes())
                .endTime(session.getEndTime())
                .info(session.getInfo())
                .name(session.getName())
                .startTime(session.getStartTime())
                .topic( TopicDTO.fromTopic(session.getTopic()) )
                .result(session.getSessionResult())
                .build();
    }
}
