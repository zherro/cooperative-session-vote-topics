package br.com.southsystem.cooperative.model.type;

public enum Vote {
    YES("Y"),
    NO("N");

    private String value;

    private Vote(String value) {
        this.value = value;
    }

    public String getDescription() {
        return this.value;
    }
}
