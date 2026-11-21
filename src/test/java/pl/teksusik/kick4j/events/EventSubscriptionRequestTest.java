package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Verifies serialization of our own request DTO (JSON key names), not any documented API response payload.
 */
public class EventSubscriptionRequestTest {
    // Naming as in KickClient; without final fields as mutators, only the @JsonCreator parameter name can bind.
    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS)
            .build();

    @Test
    public void request_serializes_broadcaster_user_id() {
        EventSubscriptionRequest request = EventSubscriptionRequest.builder()
                .broadcasterUserId(123456789)
                .addEvent(new EventSubscriptionRequest.Event("chat.message.sent", 1))
                .method(EventSubscriptionRequest.Method.WEBHOOK)
                .build();

        Map<String, Object> json = MAPPER.convertValue(request, new TypeReference<>() {});

        assertEquals(123456789, json.get("broadcaster_user_id"));
        assertFalse(json.containsKey("broadcast_user_id"));
        assertEquals(List.of(Map.of("name", "chat.message.sent", "version", 1)), json.get("events"));
        assertEquals("webhook", json.get("method"));
    }

    @Test
    public void request_round_trips_broadcaster_user_id() throws JsonProcessingException {
        // Event has no @JsonCreator, so the round trip uses a request without events.
        EventSubscriptionRequest request = EventSubscriptionRequest.builder()
                .broadcasterUserId(123456789)
                .method(EventSubscriptionRequest.Method.WEBHOOK)
                .build();

        String json = MAPPER.writeValueAsString(request);
        EventSubscriptionRequest deserialized = MAPPER.readValue(json, EventSubscriptionRequest.class);

        assertEquals(123456789, deserialized.getBroadcasterUserId());
        assertEquals(EventSubscriptionRequest.Method.WEBHOOK, deserialized.getMethod());
    }
}
