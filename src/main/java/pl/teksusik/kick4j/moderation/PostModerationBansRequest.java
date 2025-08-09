package pl.teksusik.kick4j.moderation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostModerationBansRequest {
    private final Integer broadcasterUserId;
    private final Integer duration;
    private final String reason;
    private final Integer userId;

    @JsonCreator
    public PostModerationBansRequest(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                                     @JsonProperty("duration") Integer duration,
                                     @JsonProperty("reason") String reason,
                                     @JsonProperty("user_id") Integer userId) {
        this.broadcasterUserId = broadcasterUserId;
        this.duration = duration;
        this.reason = reason;
        this.userId = userId;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getReason() {
        return reason;
    }

    public Integer getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer broadcasterUserId;
        private Integer duration;
        private String reason;
        private Integer userId;

        public Builder broadcasterUserId(Integer broadcasterUserId) {
            this.broadcasterUserId = broadcasterUserId;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public PostModerationBansRequest build() {
            if (broadcasterUserId == null) {
                throw new IllegalStateException("BroadcasterUserId is required");
            }

            if (userId == null) {
                throw new IllegalStateException("UserId is required");
            }

            return new PostModerationBansRequest(broadcasterUserId, duration, reason, userId);
        }
    }
}
