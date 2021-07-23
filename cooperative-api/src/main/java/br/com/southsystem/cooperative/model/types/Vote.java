package br.com.southsystem.cooperative.model.types;

public enum Vote {
    YES("Y"),
    NO("N");

    private String value;

    private Vote(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
