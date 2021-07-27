package br.com.southsystem.cooperative.exceptions;

public class MessageNotPublishedExceptionSupress extends RuntimeException {

    public MessageNotPublishedExceptionSupress() {
        super("Message not published");
    }
}
