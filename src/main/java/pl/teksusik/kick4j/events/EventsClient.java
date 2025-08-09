package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class EventsClient extends ApiClient {
    public EventsClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    public List<EventSubscription> getEventsSubscriptions() {
        return this.get(this.configuration.getEvents())
                .send(new TypeReference<>() {});
    }

    public List<EventSubscriptionResponse> postEventsSubscription(EventSubscriptionRequest request) {
        return this.post(this.configuration.getEvents())
                .body(request)
                .send(new TypeReference<>() {});
    }

    public void deleteEventsSubscriptions(String... ids) {
        this.delete(this.configuration.getEvents())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }

    public void deleteEventsSubscriptions(List<String> ids) {
        this.delete(this.configuration.getEvents())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }
}
