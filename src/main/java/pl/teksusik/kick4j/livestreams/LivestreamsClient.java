package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;

public class LivestreamsClient extends ApiClient {
    public LivestreamsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    public Livestream getLivestream(Integer broadcasterUserId) {
        return this.getLivestreams(GetLivestreamsRequest.builder()
                .broadcasterUserId(broadcasterUserId).build())
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Livestream> getLivestreams(GetLivestreamsRequest request) {
        return this.get(this.configuration.getLivestreams())
                .queryParams(request)
                .send(new TypeReference<>() {});
    }
}
