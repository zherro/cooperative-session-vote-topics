package br.com.southsystem.cooperative.controller.doc.model;

import br.com.southsystem.usersapi.doc.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatorResponse {
    private String doc;
    private UserStatus status;
}