package pl.teksusik.kick4j.drops;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class DropClaim {
    private final String claimId;
    private final Integer userId;
    private final String campaignId;
    private final String rewardId;
    private final String externalId;
    private final String externalStatus;
    private final Instant createdAt;
    private final Instant updatedAt;

    @JsonCreator
    public DropClaim(@JsonProperty("claim_id") String claimId,
                     @JsonProperty("user_id") Integer userId,
                     @JsonProperty("campaign_id") String campaignId,
                     @JsonProperty("reward_id") String rewardId,
                     @JsonProperty("external_id") String externalId,
                     @JsonProperty("external_status") String externalStatus,
                     @JsonProperty("created_at") Instant createdAt,
                     @JsonProperty("updated_at") Instant updatedAt) {
        this.claimId = claimId;
        this.userId = userId;
        this.campaignId = campaignId;
        this.rewardId = rewardId;
        this.externalId = externalId;
        this.externalStatus = externalStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getClaimId() {
        return claimId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getRewardId() {
        return rewardId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getExternalStatus() {
        return externalStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
