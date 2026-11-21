package pl.teksusik.kick4j.api;

public class ApiException extends RuntimeException {
    private final int errorCode;
    private final String payload;
    private final String reason;

    public ApiException(int errorCode, String payload) {
        this(errorCode, payload, null);
    }

    public ApiException(int errorCode, String payload, String reason) {
        super(reason == null
                ? String.format("API request failed %s. %s", errorCode, payload)
                : String.format("API request failed %s (%s). %s", errorCode, reason, payload));
        this.errorCode = errorCode;
        this.payload = payload;
        this.reason = reason;
    }

    /**
     * HTTP status code of the failed response.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * The {@code message} field of the error response, or the raw body if it isn't a Kick JSON envelope.
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Machine-readable error code Kick puts in {@code data} for some errors
     * (e.g. {@code AD_BREAK_ACTIVE}, {@code CHANNEL_IS_LIVE}), or {@code null}.
     */
    public String getReason() {
        return reason;
    }
}
