package pl.teksusik.kick4j.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int errorCode;
    private final String payload;

    public ApiException(int errorCode, String payload) {
        super(String.format("API request failed %s. %s", errorCode, payload));
        this.errorCode = errorCode;
        this.payload = payload;
    }
}
