package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FailedRedemption {
    private final String id;
    private final FailedRedemptionReason reason;

    @JsonCreator
    public FailedRedemption(@JsonProperty("id") String id,
                            @JsonProperty("reason") FailedRedemptionReason reason) {
        this.id = id;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public FailedRedemptionReason getReason() {
        return reason;
    }
}
