package pl.teksusik.kick4j.moderation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;

public class ModerationClient extends ApiClient {
    public ModerationClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    public void postModerationBans(PostModerationBansRequest request) {
        this.post(this.configuration.getModeration())
                .body(request)
                .send(new TypeReference<>() {});
    }

    public void deleteModerationBans(DeleteModerationBansRequest request) {
        this.delete(this.configuration.getModeration())
                .body(request)
                .send(new TypeReference<>() {});
    }

    public void deleteModerationBans(Integer broadcasterUserId, Integer userId) {
        this.deleteModerationBans(new DeleteModerationBansRequest(broadcasterUserId, userId));
    }
}
