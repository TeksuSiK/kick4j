package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    private final T data;
    private final String message;

    @JsonCreator
    public ApiResponse(@JsonProperty("data") T data, @JsonProperty("message") String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return data != null;
    }
}
