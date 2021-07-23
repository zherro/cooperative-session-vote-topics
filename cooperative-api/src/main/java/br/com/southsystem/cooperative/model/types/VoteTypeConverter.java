package br.com.southsystem.cooperative.model.types;

import java.util.stream.Stream;
import javax.persistence.AttributeConverter;

public class VoteTypeConverter implements AttributeConverter<Vote, String> {
    @Override
    public String convertToDatabaseColumn(Vote vote) {
        return vote.getValue();
    }

    @Override
    public Vote convertToEntityAttribute(String vote) {
        return Stream.of(Vote.values())
                .filter(t -> t.getValue().equalsIgnoreCase(vote))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Vote: " + vote));
    }
}
