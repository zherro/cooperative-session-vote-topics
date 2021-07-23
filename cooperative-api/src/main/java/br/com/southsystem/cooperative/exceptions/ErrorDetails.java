package br.com.southsystem.cooperative.exceptions;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorDetails {

    private String title;
    private String msg;
    private int code;
}
