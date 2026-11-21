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

    /**
     * Lists event subscriptions via {@code GET /public/v1/events/subscriptions}.
     * Works with a user or app access token. Not paginated.
     */
    public List<EventSubscription> getEventsSubscriptions() {
        return this.get(this.configuration.getEvents())
                .send(new TypeReference<>() {});
    }

    /**
     * Lists event subscriptions for the given broadcaster via
     * {@code GET /public/v1/events/subscriptions?broadcaster_user_id=...}.
     * Works with a user or app access token; with an app token it filters the app's
     * subscriptions to that broadcaster. Not paginated.
     */
    public List<EventSubscription> getEventsSubscriptions(int broadcasterUserId) {
        return this.get(this.configuration.getEvents())
                .queryParams(Map.of("broadcaster_user_id", broadcasterUserId))
                .send(new TypeReference<>() {});
    }

    /**
     * Subscribes to events via {@code POST /public/v1/events/subscriptions}.
     * Requires a user access token with scope {@code events:subscribe}, or an app access token.
     */
    public List<EventSubscriptionResponse> postEventsSubscription(EventSubscriptionRequest request) {
        return this.post(this.configuration.getEvents())
                .body(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Deletes event subscriptions by ID via {@code DELETE /public/v1/events/subscriptions}.
     * Requires a user access token with scope {@code events:subscribe}, or an app access token.
     */
    public void deleteEventsSubscriptions(String... ids) {
        this.delete(this.configuration.getEvents())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }

    /**
     * Deletes event subscriptions by ID via {@code DELETE /public/v1/events/subscriptions}.
     * Requires a user access token with scope {@code events:subscribe}, or an app access token.
     */
    public void deleteEventsSubscriptions(List<String> ids) {
        this.delete(this.configuration.getEvents())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }
}
