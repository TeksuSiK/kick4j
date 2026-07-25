package pl.teksusik.kick4j.drops;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filters for {@code GET /drops/claims}. All fields are optional.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDropClaimsRequest {
    @JsonProperty("campaign_id")
    private final String campaignId;
    @JsonProperty("limit")
    private final Integer limit;
    @JsonProperty("cursor")
    private final String cursor;
    @JsonProperty("user_id")
    private final Integer userId;
    @JsonProperty("claim_id")
    private final String claimId;
    @JsonProperty("external_status")
    private final String externalStatus;

    public GetDropClaimsRequest(String campaignId, Integer limit, String cursor, Integer userId,
                                String claimId, String externalStatus) {
        this.campaignId = campaignId;
        this.limit = limit;
        this.cursor = cursor;
        this.userId = userId;
        this.claimId = claimId;
        this.externalStatus = externalStatus;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getCursor() {
        return cursor;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getClaimId() {
        return claimId;
    }

    public String getExternalStatus() {
        return externalStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String campaignId;
        private Integer limit;
        private String cursor;
        private Integer userId;
        private String claimId;
        private String externalStatus;

        public Builder campaignId(String campaignId) {
            this.campaignId = campaignId;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder claimId(String claimId) {
            this.claimId = claimId;
            return this;
        }

        public Builder externalStatus(String externalStatus) {
            this.externalStatus = externalStatus;
            return this;
        }

        public GetDropClaimsRequest build() {
            return new GetDropClaimsRequest(campaignId, limit, cursor, userId, claimId, externalStatus);
        }
    }
}
