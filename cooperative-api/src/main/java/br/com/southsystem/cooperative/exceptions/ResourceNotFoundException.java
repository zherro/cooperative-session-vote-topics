package br.com.southsystem.cooperative.exceptions;

import org.springframework.context.MessageSource;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(MessageSource messageSource, String label, String key) {
        this(getMsg(messageSource, label, key));
    }

    private static String getMsg(MessageSource messageSource, String label, String key) {
        var msg = MessageService.getMessage(messageSource,"exceptions.msg-with-param.resource-not-found");
        var labelText = MessageService.getMessage(messageSource, label);
        return String.format(msg, labelText, key);
    }
}
