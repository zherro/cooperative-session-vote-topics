package br.com.southsystem.cooperative.exceptions;

import org.springframework.context.MessageSource;

public interface MessageService {

    static String getMessage(MessageSource messageSource, String message) {
        return messageSource.getMessage(message, null, "Default", null);
    }

    static void createBusinessException(MessageSource messageSource, AppMessages msgExceptionUsernameInUse) {
        var msg = MessageService.getMessage(messageSource, msgExceptionUsernameInUse.getMsgKey());
        throw new BusinessException(msg);
    }
}
