package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.UUID;

/**
 * Ads API. Every endpoint acts on the authenticated broadcaster's channel and requires a
 * user access token.
 */
public class AdsClient extends ApiClient {
    public AdsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * Creates an ad break ({@code POST /ads/ad-break}). Requires the {@code ads:write} scope.
     * The API answers 202 Accepted; the ad break is not guaranteed to be running yet, so poll
     * {@link #getAdBreakStatus()} to follow it.
     */
    public AdBreak createAdBreak(CreateAdBreakRequest request) {
        return this.post(this.configuration.getAdsAdBreak())
                .body(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Creates an ad break with a freshly generated random UUID as its {@code id}.
     * Requires the {@code ads:write} scope.
     *
     * @param breakDurationSeconds ad break duration in seconds (7-90)
     */
    public AdBreak createAdBreak(int breakDurationSeconds) {
        return this.createAdBreak(new CreateAdBreakRequest(breakDurationSeconds, UUID.randomUUID().toString()));
    }

    /**
     * Gets the ad break status of the channel ({@code GET /ads/ad-break-status}): recent ad
     * breaks, limits and opt-in state. Requires the {@code ads:read} or {@code ads:write} scope.
     */
    public AdBreakStatus getAdBreakStatus() {
        return this.get(this.configuration.getAdsAdBreakStatus())
                .send(new TypeReference<>() {});
    }

    /**
     * Enrolls the channel in ads ({@code POST /ads/enroll}). Requires the {@code ads:write}
     * scope. The API rejects enrollment while the channel is live or if it has never streamed.
     */
    public void enroll() {
        this.post(this.configuration.getAdsEnroll())
                .send(new TypeReference<>() {});
    }
}
