package br.com.southsystem.cooperative.dto.session;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SessionCreateDTO {
    private String topicId;
    private String name;
    private String info;

    private LocalDateTime startTime;
    private int durationMinutes = 1;
}
