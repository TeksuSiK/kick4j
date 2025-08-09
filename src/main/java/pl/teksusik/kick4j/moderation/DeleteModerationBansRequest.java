package pl.teksusik.kick4j.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteModerationBansRequest {
    private final Integer broadcasterUserId;
    private final Integer userId;

    public DeleteModerationBansRequest(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                                       @JsonProperty("user_id") Integer userId) {
        this.broadcasterUserId = broadcasterUserId;
        this.userId = userId;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer broadcasterUserId;
        private Integer userId;

        public Builder broadcasterUserId(Integer broadcasterUserId) {
            this.broadcasterUserId = broadcasterUserId;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public DeleteModerationBansRequest build() {
            if (broadcasterUserId == null) {
                throw new IllegalStateException("BroadcasterUserId is required");
            }

            if (userId == null) {
                throw new IllegalStateException("UserId is required");
            }

            return new DeleteModerationBansRequest(broadcasterUserId, userId);
        }
    }
}
