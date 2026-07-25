package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class ChannelRewardRedemption {
    private final String id;
    private final Instant redeemedAt;
    private final Redeemer redeemer;
    private final RedemptionStatus status;
    private final String userInput;

    @JsonCreator
    public ChannelRewardRedemption(@JsonProperty("id") String id,
                                   @JsonProperty("redeemed_at") Instant redeemedAt,
                                   @JsonProperty("redeemer") Redeemer redeemer,
                                   @JsonProperty("status") RedemptionStatus status,
                                   @JsonProperty("user_input") String userInput) {
        this.id = id;
        this.redeemedAt = redeemedAt;
        this.redeemer = redeemer;
        this.status = status;
        this.userInput = userInput;
    }

    public String getId() {
        return id;
    }

    public Instant getRedeemedAt() {
        return redeemedAt;
    }

    public Redeemer getRedeemer() {
        return redeemer;
    }

    public RedemptionStatus getStatus() {
        return status;
    }

    public String getUserInput() {
        return userInput;
    }
}
