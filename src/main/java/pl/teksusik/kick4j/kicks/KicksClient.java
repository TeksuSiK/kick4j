package pl.teksusik.kick4j.kicks;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.Map;

public class KicksClient extends ApiClient {
    public KicksClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * Gets the KICKs leaderboard for the authenticated broadcaster, using the API default
     * of the top 10 entries.
     */
    public KicksLeaderboard getLeaderboard() {
        return this.get(this.configuration.getKicksLeaderboard())
                .send(new TypeReference<>() {});
    }

    /**
     * Gets the KICKs leaderboard for the authenticated broadcaster.
     *
     * @param top number of top entries to return (1-100)
     */
    public KicksLeaderboard getLeaderboard(int top) {
        return this.get(this.configuration.getKicksLeaderboard())
                .queryParams(Map.of("top", top))
                .send(new TypeReference<>() {});
    }
}
