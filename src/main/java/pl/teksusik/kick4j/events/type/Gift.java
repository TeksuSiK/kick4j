package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Gift {
    private final Integer amount;
    private final String name;
    private final String type;
    private final String tier;
    private final String message;
    private final Integer pinnedTimeSeconds;

    @JsonCreator
    public Gift(@JsonProperty("amount") Integer amount,
                @JsonProperty("name") String name,
                @JsonProperty("type") String type,
                @JsonProperty("tier") String tier,
                @JsonProperty("message") String message,
                @JsonProperty("pinned_time_seconds") Integer pinnedTimeSeconds
    ) {
        this.amount = amount;
        this.name = name;
        this.type = type;
        this.tier = tier;
        this.message = message;
        this.pinnedTimeSeconds = pinnedTimeSeconds;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getTier() {
        return this.tier;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getPinnedTimeSeconds() {
        return this.pinnedTimeSeconds;
    }
}
