package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.rewards.RedemptionStatus;

import java.time.Instant;

public class ChannelRewardRedemptionUpdatedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final String id;
    private final Instant redeemedAt;
    private final EventUser redeemer;
    private final RedemptionReward reward;
    private final RedemptionStatus status;
    private final String userInput;

    @JsonCreator
    public ChannelRewardRedemptionUpdatedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                               @JsonProperty("id") String id,
                                               @JsonProperty("redeemed_at") Instant redeemedAt,
                                               @JsonProperty("redeemer") EventUser redeemer,
                                               @JsonProperty("reward") RedemptionReward reward,
                                               @JsonProperty("status") RedemptionStatus status,
                                               @JsonProperty("user_input") String userInput) {
        this.broadcaster = broadcaster;
        this.id = id;
        this.redeemedAt = redeemedAt;
        this.redeemer = redeemer;
        this.reward = reward;
        this.status = status;
        this.userInput = userInput;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public String getId() {
        return id;
    }

    public Instant getRedeemedAt() {
        return redeemedAt;
    }

    public EventUser getRedeemer() {
        return redeemer;
    }

    public RedemptionReward getReward() {
        return reward;
    }

    public RedemptionStatus getStatus() {
        return status;
    }

    public String getUserInput() {
        return userInput;
    }

    public static String getEventType() {
        return "channel.reward.redemption.updated";
    }

    public static String getEventVersion() {
        return "1";
    }
}
