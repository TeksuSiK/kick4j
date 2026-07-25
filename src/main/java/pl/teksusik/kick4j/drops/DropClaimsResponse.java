package pl.teksusik.kick4j.drops;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A page of Drops reward claims. Unlike the generic paginated endpoints, the cursor
 * is nested inside the {@code data} object; pass {@link #getCursor()} back as the
 * {@code cursor} filter to fetch the next page.
 */
public class DropClaimsResponse {
    private final List<DropClaim> claims;
    private final String cursor;

    @JsonCreator
    public DropClaimsResponse(@JsonProperty("claims") List<DropClaim> claims,
                              @JsonProperty("cursor") String cursor) {
        this.claims = claims;
        this.cursor = cursor;
    }

    public List<DropClaim> getClaims() {
        return claims;
    }

    public String getCursor() {
        return cursor;
    }
}
