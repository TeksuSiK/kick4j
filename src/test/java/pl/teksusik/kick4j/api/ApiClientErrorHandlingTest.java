package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.AuthMode;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiClientErrorHandlingTest {
    private final HttpClient httpClient = mock(HttpClient.class);
    private final AuthorizationClient authorization = mock(AuthorizationClient.class);
    private final TestClient client = new TestClient(httpClient, mapper(), KickConfiguration.builder()
            .clientId("client-id")
            .clientSecret("client-secret")
            .authMode(AuthMode.USER)
            .build(), authorization);

    @AfterEach
    public void clearInterruptFlag() {
        Thread.interrupted();
    }

    @Test
    public void error_with_textual_data_throws_api_exception_with_reason() throws Exception {
        respond(400, """
                {"data": "AD_BREAK_ACTIVE", "message": "An ad break is already running"}
                """);

        ApiException exception = assertThrows(ApiException.class, client::fetchObject);
        assertEquals(400, exception.getErrorCode());
        assertEquals("An ad break is already running", exception.getPayload());
        assertEquals("AD_BREAK_ACTIVE", exception.getReason());
    }

    @Test
    public void error_with_null_data_throws_api_exception_without_reason() throws Exception {
        respond(401, """
                {"data": null, "message": "Unauthorized"}
                """);

        ApiException exception = assertThrows(ApiException.class, client::fetchObject);
        assertEquals(401, exception.getErrorCode());
        assertEquals("Unauthorized", exception.getPayload());
        assertNull(exception.getReason());
    }

    @Test
    public void error_with_empty_body_throws_instead_of_returning_null() throws Exception {
        respond(403, "");

        ApiException exception = assertThrows(ApiException.class, client::fetchObject);
        assertEquals(403, exception.getErrorCode());
    }

    @Test
    public void error_with_non_json_body_keeps_raw_body() throws Exception {
        respond(502, "<html>Bad Gateway</html>");

        ApiException exception = assertThrows(ApiException.class, client::fetchRaw);
        assertEquals(502, exception.getErrorCode());
        assertEquals("<html>Bad Gateway</html>", exception.getPayload());
    }

    @Test
    public void raw_error_with_textual_data_exposes_reason() throws Exception {
        respond(422, """
                {"data": "CHANNEL_IS_LIVE", "message": "Channel is live"}
                """);

        ApiException exception = assertThrows(ApiException.class, client::fetchRaw);
        assertEquals("CHANNEL_IS_LIVE", exception.getReason());
    }

    @Test
    public void success_returns_data() throws Exception {
        respond(200, """
                {"data": {"name": "kick4j"}, "message": "OK"}
                """);

        assertEquals("kick4j", client.fetchObject().get("name"));
    }

    @Test
    public void io_error_does_not_interrupt_thread() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any())).thenThrow(new IOException("connection reset"));

        assertThrows(RuntimeException.class, client::fetchObject);
        assertFalse(Thread.currentThread().isInterrupted());
    }

    @Test
    public void interruption_restores_interrupt_flag() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any())).thenThrow(new InterruptedException());

        assertThrows(RuntimeException.class, client::fetchObject);
        assertTrue(Thread.currentThread().isInterrupted());
    }

    @SuppressWarnings("unchecked")
    private void respond(int statusCode, String body) throws Exception {
        HttpResponse<String> response = mock(HttpResponse.class);
        when(response.statusCode()).thenReturn(statusCode);
        when(response.body()).thenReturn(body);
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);
    }

    private static ObjectMapper mapper() {
        SimpleModule module = new SimpleModule()
                .addDeserializer(ApiResponse.class, new ApiResponseDeserializer<>());
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(module)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private static class TestClient extends ApiClient {
        TestClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
            super(httpClient, mapper, configuration, authorization);
        }

        Map<String, Object> fetchObject() {
            return this.get("/test").send(new TypeReference<>() {});
        }

        List<Object> fetchRaw() {
            return this.get("/test").sendRaw(new TypeReference<>() {});
        }
    }
}
