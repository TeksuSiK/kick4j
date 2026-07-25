package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RedemptionsByReward {
    private final List<ChannelRewardRedemption> redemptions;
    private final MinimalChannelReward reward;

    @JsonCreator
    public RedemptionsByReward(@JsonProperty("redemptions") List<ChannelRewardRedemption> redemptions,
                               @JsonProperty("reward") MinimalChannelReward reward) {
        this.redemptions = redemptions;
        this.reward = reward;
    }

    public List<ChannelRewardRedemption> getRedemptions() {
        return redemptions;
    }

    public MinimalChannelReward getReward() {
        return reward;
    }
}
