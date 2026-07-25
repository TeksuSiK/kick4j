package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateChannelRewardRequest {
    @JsonProperty("background_color")
    private final String backgroundColor;
    @JsonProperty("cost")
    private final Integer cost;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("is_enabled")
    private final Boolean isEnabled;
    @JsonProperty("is_user_input_required")
    private final Boolean isUserInputRequired;
    @JsonProperty("should_redemptions_skip_request_queue")
    private final Boolean shouldRedemptionsSkipRequestQueue;
    @JsonProperty("title")
    private final String title;

    public CreateChannelRewardRequest(String backgroundColor, Integer cost, String description, Boolean isEnabled,
                                      Boolean isUserInputRequired, Boolean shouldRedemptionsSkipRequestQueue, String title) {
        this.backgroundColor = backgroundColor;
        this.cost = cost;
        this.description = description;
        this.isEnabled = isEnabled;
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

        public CreateChannelRewardRequest build() {
            if (title == null) {
                throw new IllegalStateException("Title is required");
            }

            if (cost == null) {
                throw new IllegalStateException("Cost is required");
            }

            return new CreateChannelRewardRequest(backgroundColor, cost, description, isEnabled,
                    isUserInputRequired, shouldRedemptionsSkipRequestQueue, title);
        }
    }
}
