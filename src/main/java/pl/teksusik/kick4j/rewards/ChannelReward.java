package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelReward {
    private final String backgroundColor;
    private final Integer cost;
    private final String description;
    private final String id;
    private final Boolean isEnabled;
    private final Boolean isPaused;
    private final Boolean isUserInputRequired;
    private final Boolean shouldRedemptionsSkipRequestQueue;
    private final String title;

    @JsonCreator
    public ChannelReward(@JsonProperty("background_color") String backgroundColor,
                         @JsonProperty("cost") Integer cost,
                         @JsonProperty("description") String description,
                         @JsonProperty("id") String id,
                         @JsonProperty("is_enabled") Boolean isEnabled,
                         @JsonProperty("is_paused") Boolean isPaused,
                         @JsonProperty("is_user_input_required") Boolean isUserInputRequired,
                         @JsonProperty("should_redemptions_skip_request_queue") Boolean shouldRedemptionsSkipRequestQueue,
                         @JsonProperty("title") String title) {
        this.backgroundColor = backgroundColor;
        this.cost = cost;
        this.description = description;
        this.id = id;
        this.isEnabled = isEnabled;
        this.isPaused = isPaused;
        this.isUserInputRequired = isUserInputRequired;
        this.shouldRedemptionsSkipRequestQueue = shouldRedemptionsSkipRequestQueue;
        this.title = title;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Integer getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public Boolean isPaused() {
        return isPaused;
    }

    public Boolean isUserInputRequired() {
        return isUserInputRequired;
    }

    public Boolean shouldRedemptionsSkipRequestQueue() {
        return shouldRedemptionsSkipRequestQueue;
    }

    public String getTitle() {
        return title;
    }
}
