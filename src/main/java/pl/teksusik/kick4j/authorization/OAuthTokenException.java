package pl.teksusik.kick4j.authorization;

public class OAuthTokenException extends RuntimeException {
    private final int errorCode;
    private final String payload;

    public OAuthTokenException(int errorCode, String payload) {
        super(String.format("Authorization failed: %s. %s", errorCode, payload));
        this.errorCode = errorCode;
        this.payload = payload;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getPayload() {
        return payload;
    }
}