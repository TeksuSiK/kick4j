package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KicksGift {
    private final Integer amount;
    private final String message;
    private final String name;
    private final Integer pinnedTimeSeconds;
    private final String tier;
    private final String type;

    @JsonCreator
    public KicksGift(@JsonProperty("amount") Integer amount,
                     @JsonProperty("message") String message,
                     @JsonProperty("name") String name,
                     @JsonProperty("pinned_time_seconds") Integer pinnedTimeSeconds,
                     @JsonProperty("tier") String tier,
                     @JsonProperty("type") String type) {
        this.amount = amount;
        this.message = message;
        this.name = name;
        this.pinnedTimeSeconds = pinnedTimeSeconds;
        this.tier = tier;
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public Integer getPinnedTimeSeconds() {
        return pinnedTimeSeconds;
    }

    public String getTier() {
        return tier;
    }

    public String getType() {
        return type;
    }
}
