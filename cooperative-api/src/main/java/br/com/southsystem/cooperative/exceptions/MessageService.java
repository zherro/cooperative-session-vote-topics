package br.com.southsystem.cooperative.exceptions;

import org.springframework.context.MessageSource;

public interface MessageService {

    static String getMessage(MessageSource messageSource, String message) {
        return messageSource.getMessage(message, null, "Default", null);
    }
}
