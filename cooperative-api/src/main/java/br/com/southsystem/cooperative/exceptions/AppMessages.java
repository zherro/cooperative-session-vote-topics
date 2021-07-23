package br.com.southsystem.cooperative.exceptions;

public enum AppMessages {
    MSG_EXCEPTION_SESSION_STARTED("exceptions.msg.session-expired"),
    MSG_EXCEPTION_HAS_SESSION_ACTIVE("exceptions.msg.has-session-active"),
    ;

    private String msgKey;

    AppMessages(String msgKey)    {
        this.msgKey = msgKey;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
