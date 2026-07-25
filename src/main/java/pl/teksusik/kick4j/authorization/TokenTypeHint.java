package pl.teksusik.kick4j.authorization;

public enum TokenTypeHint {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String value;

    TokenTypeHint(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
