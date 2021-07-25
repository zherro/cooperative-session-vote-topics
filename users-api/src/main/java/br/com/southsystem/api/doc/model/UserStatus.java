package br.com.southsystem.cooperative.controller.doc.model;

public enum UserStatus {
    ABLE_TO_VOTE(true), UNABLE_TO_VOTE(false);

    private boolean value;

    UserStatus(boolean value)  {
        this.value = value;
    }

    public static UserStatus of(boolean value) {
        if(value) {
            return ABLE_TO_VOTE;
        }
        return UNABLE_TO_VOTE;
    }
}
