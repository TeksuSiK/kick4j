package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.AuthMode;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Verifies the requests {@link EventsClient} builds, using a mocked {@link HttpClient}.
 */
public class EventsClientTest {
    private HttpClient httpClient;
    private EventsClient eventsClient;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        this.httpClient = mock(HttpClient.class);
        AuthorizationClient authorization = mock(AuthorizationClient.class);
        when(authorization.getAppAccessToken()).thenReturn("app-token");

        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\"data\":[],\"message\":\"OK\"}");
        when(this.httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        KickConfiguration configuration = KickConfiguration.builder()
                .clientId("client-id")
                .clientSecret("client-secret")
                .authMode(AuthMode.APP)
                .build();
        this.eventsClient = new EventsClient(this.httpClient, new ObjectMapper(), configuration, authorization);
    }

    @Test
    public void get_subscriptions_without_filter_sends_no_query_string() throws Exception {
        this.eventsClient.getEventsSubscriptions();

        URI uri = this.captureRequest().uri();
        assertEquals("/public/v1/events/subscriptions", uri.getPath());
        assertNull(uri.getQuery());
    }

    @Test
    public void get_subscriptions_by_broadcaster_sends_broadcaster_user_id() throws Exception {
        this.eventsClient.getEventsSubscriptions(123456);

        HttpRequest request = this.captureRequest();
        assertEquals("GET", request.method());
        assertEquals("/public/v1/events/subscriptions", request.uri().getPath());
        assertEquals("broadcaster_user_id=123456", request.uri().getQuery());
        assertEquals("Bearer app-token", request.headers().firstValue("Authorization").orElseThrow());
    }

    @SuppressWarnings("unchecked")
    private HttpRequest captureRequest() throws Exception {
        ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(this.httpClient).send(captor.capture(), any(HttpResponse.BodyHandler.class));
        return captor.getValue();
    }
}
