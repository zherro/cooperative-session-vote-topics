package br.com.southsystem.cooperative.dto.session;

import br.com.southsystem.cooperative.dto.topic.TopicDTO;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class SessionDTO {
    private final String id;
    private final TopicDTO topic;
    private final String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime endTime;

    private final int durationMinutes;
    private final String info;

    private final boolean active;
    private final List<VoteSummary> result;

    private boolean running;
    private boolean ended;

    public void verifyState() {
        running = this.startTime.isBefore(LocalDateTime.now()) && this.endTime.isAfter(LocalDateTime.now());
        ended = this.active && this.endTime.isBefore(LocalDateTime.now());
    }

    public static SessionDTO fromSession(Session session) {
        var dto = session == null ? null :
                SessionDTO.builder()
                .id(session.getUuid())
                .active(session.isActive())
                .durationMinutes(session.getDurationMinutes())
                .endTime(session.getEndTime())
                .info(session.getInfo())
                .name(session.getName())
                .startTime(session.getStartTime())
                .topic( session.getTopic() == null ? null : TopicDTO.fromTopic(session.getTopic()) )
                .result(session.getSessionResult())
                .build();
        if(dto != null) {
            dto.verifyState();
        }
        return dto;
    }
}
