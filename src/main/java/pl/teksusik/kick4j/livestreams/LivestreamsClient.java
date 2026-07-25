package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.api.PaginatedResponse;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class LivestreamsClient extends ApiClient {
    public LivestreamsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * @deprecated The V1 livestreams endpoint is deprecated by Kick. Use
     * {@link #getLivestreamsV2(GetLivestreamsV2Request)} or {@link #getUserLivestreams(List)} instead.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public Livestream getLivestream(Integer broadcasterUserId) {
        return this.getLivestreams(GetLivestreamsRequest.builder()
                .broadcasterUserId(broadcasterUserId).build())
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * @deprecated The V1 livestreams endpoint is deprecated by Kick. Use
     * {@link #getLivestreamsV2(GetLivestreamsV2Request)} or {@link #getUserLivestreams(List)} instead.
     */
    @Deprecated
    public List<Livestream> getLivestreams(GetLivestreamsRequest request) {
        return this.get(this.configuration.getLivestreams())
                .queryParams(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Lists active livestreams via the paginated V2 endpoint, with default filters.
     */
    public PaginatedResponse<LivestreamV2> getLivestreamsV2() {
        return this.getLivestreamsV2(GetLivestreamsV2Request.builder().build());
    }

    /**
     * Lists active livestreams via the paginated V2 endpoint using the provided filters.
     * Results are sorted oldest to newest. Use {@link PaginatedResponse#getPagination()} to page.
     */
    public PaginatedResponse<LivestreamV2> getLivestreamsV2(GetLivestreamsV2Request request) {
        return this.get(this.configuration.getLivestreams())
                .baseUrl(this.configuration.getBaseUrlV2())
                .queryParams(request)
                .sendRaw(new TypeReference<>() {});
    }

    /**
     * Gets the active livestreams for the given broadcaster user IDs (max 100).
     */
    public List<LivestreamV2> getUserLivestreams(List<Integer> userIds) {
        return this.get(this.configuration.getUsersLivestreams())
                .queryParams(Map.of("user_id", userIds))
                .send(new TypeReference<>() {});
    }

    public List<LivestreamV2> getUserLivestreams(int... userIds) {
        return this.get(this.configuration.getUsersLivestreams())
                .queryParams(Map.of("user_id", userIds))
                .send(new TypeReference<>() {});
    }

    public LivestreamsStats getLivestreamsStats() {
        return this.get(this.configuration.getLivestreamsStats())
                .send(new TypeReference<>() {});
    }
}
