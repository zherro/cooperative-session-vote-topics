package br.com.southsystem.cooperative.model.type;

public enum Vote {
    S("SIM"),
    N("NÃO");

    private String description;

    private Vote(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
