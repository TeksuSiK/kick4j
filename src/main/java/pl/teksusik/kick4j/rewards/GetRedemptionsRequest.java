package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Filters for listing channel reward redemptions.
 * <p>
 * Note: when filtering by redemption {@code ids}, no other filter may be provided.
 * The {@code cursor} lets callers page manually; the API's {@code next_cursor} is not
 * surfaced by the response models yet (pending generic pagination support).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRedemptionsRequest {
    @JsonProperty("reward_id")
    private final String rewardId;
    @JsonProperty("status")
    private final RedemptionStatus status;
    @JsonProperty("id")
    private final List<String> ids;
    @JsonProperty("cursor")
    private final String cursor;

    public GetRedemptionsRequest(String rewardId, RedemptionStatus status, List<String> ids, String cursor) {
        this.rewardId = rewardId;
        this.status = status;
        this.ids = ids;
        this.cursor = cursor;
    }

    public String getRewardId() {
        return rewardId;
    }

    public RedemptionStatus getStatus() {
        return status;
    }

    public List<String> getIds() {
        return ids;
    }

    public String getCursor() {
        return cursor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String rewardId;
        private RedemptionStatus status;
        private List<String> ids;
        private String cursor;

        public Builder rewardId(String rewardId) {
            this.rewardId = rewardId;
            return this;
        }

        public Builder status(RedemptionStatus status) {
            this.status = status;
            return this;
        }

        public Builder ids(List<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public GetRedemptionsRequest build() {
            return new GetRedemptionsRequest(rewardId, status, ids, cursor);
        }
    }
}
