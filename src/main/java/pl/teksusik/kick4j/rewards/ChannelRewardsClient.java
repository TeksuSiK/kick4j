package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.api.PaginatedResponse;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class ChannelRewardsClient extends ApiClient {
    public ChannelRewardsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * Lists channel rewards for the authenticated broadcaster's channel (up to 15).
     */
    public List<ChannelReward> getRewards() {
        return this.get(this.configuration.getChannelsRewards())
                .send(new TypeReference<>() {});
    }

    /**
     * Creates a channel reward. A maximum of 15 rewards can exist per channel.
     */
    public ChannelReward createReward(CreateChannelRewardRequest request) {
        return this.post(this.configuration.getChannelsRewards())
                .body(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Updates an existing channel reward.
     */
    public ChannelReward updateReward(String rewardId, UpdateChannelRewardRequest request) {
        return this.patch(this.configuration.getChannelsRewardsId())
                .pathParams(Map.of("id", rewardId))
                .body(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Deletes a channel reward. Only the app that created the reward can delete it.
     */
    public void deleteReward(String rewardId) {
        this.delete(this.configuration.getChannelsRewardsId())
                .pathParams(Map.of("id", rewardId))
                .send(new TypeReference<>() {});
    }

    /**
     * @deprecated Drops the pagination cursor, so only the first page is reachable.
     * Use {@link #getRedemptionsPage()} instead.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public List<RedemptionsByReward> getRedemptions() {
        return this.getRedemptions(GetRedemptionsRequest.builder().build());
    }

    /**
     * @deprecated Drops the pagination cursor, so only the first page is reachable.
     * Use {@link #getRedemptionsPage(GetRedemptionsRequest)} instead.
     */
    @Deprecated
    public List<RedemptionsByReward> getRedemptions(GetRedemptionsRequest request) {
        PaginatedResponse<RedemptionsByReward> page = this.getRedemptionsPage(request);
        return page == null ? null : page.getData();
    }

    /**
     * {@code GET /public/v1/channels/rewards/redemptions} — lists reward redemptions, grouped by reward,
     * with default filters (pending redemptions, first page).
     * Requires a user access token with {@code channel:rewards:read} or {@code channel:rewards:write}.
     */
    public PaginatedResponse<RedemptionsByReward> getRedemptionsPage() {
        return this.getRedemptionsPage(GetRedemptionsRequest.builder().build());
    }

    /**
     * {@code GET /public/v1/channels/rewards/redemptions} — lists reward redemptions, grouped by reward,
     * using the provided filters.
     * Requires a user access token with {@code channel:rewards:read} or {@code channel:rewards:write}.
     * Cursor-paginated: pass {@link PaginatedResponse#getPagination()}'s next cursor back via
     * {@link GetRedemptionsRequest.Builder#cursor(String)} to fetch the next page.
     */
    public PaginatedResponse<RedemptionsByReward> getRedemptionsPage(GetRedemptionsRequest request) {
        return this.get(this.configuration.getChannelsRewardsRedemptions())
                .queryParams(request)
                .sendRaw(new TypeReference<>() {});
    }

    /**
     * Accepts pending redemptions (up to 25 unique IDs per request).
     * Returns only the redemptions that failed to be accepted.
     */
    public List<FailedRedemption> acceptRedemptions(List<String> ids) {
        return this.post(this.configuration.getChannelsRewardsRedemptionsAccept())
                .body(Map.of("ids", ids))
                .send(new TypeReference<>() {});
    }

    public List<FailedRedemption> acceptRedemptions(String... ids) {
        return this.acceptRedemptions(List.of(ids));
    }

    /**
     * Rejects pending redemptions (up to 25 unique IDs per request).
     * Returns only the redemptions that failed to be rejected.
     */
    public List<FailedRedemption> rejectRedemptions(List<String> ids) {
        return this.post(this.configuration.getChannelsRewardsRedemptionsReject())
                .body(Map.of("ids", ids))
                .send(new TypeReference<>() {});
    }

    public List<FailedRedemption> rejectRedemptions(String... ids) {
        return this.rejectRedemptions(List.of(ids));
    }
}
