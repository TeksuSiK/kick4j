package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
    private final String nextCursor;

    @JsonCreator
    public Pagination(@JsonProperty("next_cursor") String nextCursor) {
        this.nextCursor = nextCursor;
    }

    public String getNextCursor() {
        return nextCursor;
    }
}
