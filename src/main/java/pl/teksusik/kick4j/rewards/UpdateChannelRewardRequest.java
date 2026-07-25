package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateChannelRewardRequest {
    @JsonProperty("background_color")
    private final String backgroundColor;
    @JsonProperty("cost")
    private final Integer cost;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("is_enabled")
    private final Boolean isEnabled;
    @JsonProperty("is_paused")
    private final Boolean isPaused;
    @JsonProperty("is_user_input_required")
    private final Boolean isUserInputRequired;
    @JsonProperty("should_redemptions_skip_request_queue")
    private final Boolean shouldRedemptionsSkipRequestQueue;
    @JsonProperty("title")
    private final String title;

    public UpdateChannelRewardRequest(String backgroundColor, Integer cost, String description, Boolean isEnabled,
                                      Boolean isPaused, Boolean isUserInputRequired,
                                      Boolean shouldRedemptionsSkipRequestQueue, String title) {
        this.backgroundColor = backgroundColor;
        this.cost = cost;
        this.description = description;
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

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public Boolean getIsPaused() {
        return isPaused;
    }

    public Boolean getIsUserInputRequired() {
        return isUserInputRequired;
    }

    public Boolean getShouldRedemptionsSkipRequestQueue() {
        return shouldRedemptionsSkipRequestQueue;
    }

    public String getTitle() {
        return title;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String backgroundColor;
        private Integer cost;
        private String description;
        private Boolean isEnabled;
        private Boolean isPaused;
        private Boolean isUserInputRequired;
        private Boolean shouldRedemptionsSkipRequestQueue;
        private String title;

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder cost(Integer cost) {
            this.cost = cost;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isEnabled(Boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public Builder isPaused(Boolean isPaused) {
            this.isPaused = isPaused;
            return this;
        }

        public Builder isUserInputRequired(Boolean isUserInputRequired) {
            this.isUserInputRequired = isUserInputRequired;
            return this;
        }

        public Builder shouldRedemptionsSkipRequestQueue(Boolean shouldRedemptionsSkipRequestQueue) {
            this.shouldRedemptionsSkipRequestQueue = shouldRedemptionsSkipRequestQueue;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public UpdateChannelRewardRequest build() {
            return new UpdateChannelRewardRequest(backgroundColor, cost, description, isEnabled, isPaused,
                    isUserInputRequired, shouldRedemptionsSkipRequestQueue, title);
        }
    }
}
