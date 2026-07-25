package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A cursor-paginated API response carrying both the {@code data} page and the
 * {@code pagination} cursor. Use {@link Pagination#getNextCursor()} to fetch the next page.
 *
 * @param <T> the element type of the {@code data} array
 */
public class PaginatedResponse<T> {
    private final List<T> data;
    private final String message;
    private final Pagination pagination;

    @JsonCreator
    public PaginatedResponse(@JsonProperty("data") List<T> data,
                             @JsonProperty("message") String message,
                             @JsonProperty("pagination") Pagination pagination) {
        this.data = data;
        this.message = message;
        this.pagination = pagination;
    }

    public List<T> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
