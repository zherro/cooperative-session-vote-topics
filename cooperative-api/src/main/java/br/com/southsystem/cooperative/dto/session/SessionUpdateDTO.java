package br.com.southsystem.cooperative.dto.session;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SessionUpdateDTO {
    private String name;
    private String info;

    private LocalDateTime startTime;
    private int durationMinutes;
}
