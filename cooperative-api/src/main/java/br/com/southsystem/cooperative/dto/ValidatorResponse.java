package br.com.southsystem.cooperative.dto;

import br.com.southsystem.cooperative.mock.userapi.doc.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatorResponse {
    private String doc;
    private UserStatus status;
}