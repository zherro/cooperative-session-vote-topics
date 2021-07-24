package br.com.southsystem.cooperative.model.types;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteSummary {
    private Vote vote;
    private long total;
}
