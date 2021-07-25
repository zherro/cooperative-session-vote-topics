package br.com.southsystem.cooperative.controller.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatorResponse {
    private String doc;
    private UserStatus status;
}