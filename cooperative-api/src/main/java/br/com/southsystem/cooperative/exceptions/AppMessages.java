package br.com.southsystem.cooperative.exceptions;

public enum AppMessages {
    MSG_EXCEPTION_SESSION_STARTED("exceptions.msg.session-expired"),
    MSG_EXCEPTION_HAS_SESSION_ACTIVE("exceptions.msg.has-session-active"),
    MSG_EXCEPTION_SESSION_NOT_FOUND("exceptions.msg.session-not-found"),
    MSG_EXCEPTION_SESSION_CLOSED("exceptions.msg.session-closed"),
    MSG_EXCEPTION_SESSION_NOT_STARTED("exceptions.msg.session-not-started"),
    MSG_EXCEPTION_USER_DATA_INVALID("exceptions.msg.invalid-user-data"),
    MSG_EXCEPTION_USERNAME_IN_USE("exceptions.msg.username-in-use"),
    MSG_EXCEPTION_DOCUMENT_IN_USE("exceptions.msg.document-in-use"),
    MSG_EXCEPTION_BLOCKED_USER("exceptions.msg.user-blocked"),
    MSG_EXCEPTION_INVALID_VOTE_TYPE("exceptions.msg.invalid-vote-type"),
    MSG_EXCEPTION_USER_HAS_VOTED("exceptions.msg.user-has-voted")
    ;

    private String msgKey;

    AppMessages(String msgKey)    {
        this.msgKey = msgKey;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
