package br.com.southsystem.cooperative.dto.topic;

import br.com.southsystem.cooperative.dto.session.SessionDTO;
import br.com.southsystem.cooperative.model.Topic;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class TopicDTO {

    @AllArgsConstructor
    @Data
    private class TimeLeft {
        public boolean started;
        public long days;
        public long hours;
        public long minutes;
        public long seconds;
    }

    private String id;
    private String code;
    private String theme;
    private String description;
    private boolean active;
    private boolean open;
    private TimeLeft timeLeft;

    private SessionDTO session;

    private List<VoteSummary> result = new ArrayList<>();

    public static TopicDTO fromTopic(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getUuid())
                .active(topic.isActive())
                .code(topic.getCode())
                .description(topic.getDescription())
                .open(topic.isOpen())
                .theme(topic.getTheme())
                .session(SessionDTO.fromSession(topic.getSession()))
                .build();
    }

    public void setTimeLeft(final  boolean isStarted, final LocalDateTime dateTime) {
        var now = LocalDateTime.now();

        long days = now.until( dateTime, ChronoUnit.DAYS );
        now = now.plusDays( days );

        long hours = now.until( dateTime, ChronoUnit.HOURS );
        now = now.plusHours( hours );

        long minutes = now.until( dateTime, ChronoUnit.MINUTES );
        now = now.plusMinutes( minutes );

        long seconds = now.until( dateTime, ChronoUnit.SECONDS );

        var timeLeft = new TimeLeft(isStarted, days, hours, minutes, seconds);
        this.timeLeft = timeLeft;
    }
}
