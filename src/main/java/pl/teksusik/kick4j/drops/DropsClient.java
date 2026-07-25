package pl.teksusik.kick4j.drops;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

/**
 * Drops Public API. Only OAuth apps associated with an organization can access these
 * endpoints (they require an app access token).
 */
public class DropsClient extends ApiClient {
    public DropsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * Retrieves Drops reward claims (first page, API default limit).
     */
    public DropClaimsResponse getClaims() {
        return this.getClaims(GetDropClaimsRequest.builder().build());
    }

    /**
     * Retrieves Drops reward claims using the provided filters. Page through results by
     * passing {@link DropClaimsResponse#getCursor()} back as the request cursor.
     */
    public DropClaimsResponse getClaims(GetDropClaimsRequest request) {
        return this.get(this.configuration.getDropsClaims())
                .queryParams(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Updates the {@code external_status} of one or more claims (up to 100 per request).
     * Returns on 204 No Content.
     */
    public void updateClaims(List<DropClaimUpdate> claims) {
        this.patch(this.configuration.getDropsClaims())
                .body(Map.of("claims", claims))
                .send(new TypeReference<>() {});
    }
}
