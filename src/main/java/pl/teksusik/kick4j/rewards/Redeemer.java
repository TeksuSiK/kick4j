package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Redeemer {
    private final Long userId;

    @JsonCreator
    public Redeemer(@JsonProperty("user_id") Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
