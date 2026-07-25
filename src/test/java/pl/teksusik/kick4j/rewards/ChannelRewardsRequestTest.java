package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies serialization of our own request DTOs (JSON key names and query-param mapping),
 * not any documented API response payload.
 */
public class ChannelRewardsRequestTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void create_request_serializes_snake_case_keys_and_omits_unset_fields() {
        CreateChannelRewardRequest request = CreateChannelRewardRequest.builder()
                .title("Song Request")
                .cost(100)
                .backgroundColor("#00e701")
                .build();

        Map<String, Object> json = MAPPER.convertValue(request, new TypeReference<>() {});

        assertEquals("Song Request", json.get("title"));
        assertEquals(100, json.get("cost"));
        assertEquals("#00e701", json.get("background_color"));
        // Unset optional fields must be omitted so the server applies its defaults.
        assertFalse(json.containsKey("description"));
        assertFalse(json.containsKey("is_enabled"));
        assertFalse(json.containsKey("should_redemptions_skip_request_queue"));
    }

    @Test
    public void get_redemptions_request_maps_ids_to_id_and_status_to_lowercase() {
        GetRedemptionsRequest request = GetRedemptionsRequest.builder()
                .status(RedemptionStatus.PENDING)
                .ids(List.of("01A", "01B"))
                .build();

        Map<String, Object> query = MAPPER.convertValue(request, new TypeReference<>() {});

        // The query param is "id", not "ids".
        assertEquals(List.of("01A", "01B"), query.get("id"));
        assertFalse(query.containsKey("ids"));
        assertEquals("pending", query.get("status"));
        assertFalse(query.containsKey("reward_id"));
        assertFalse(query.containsKey("cursor"));
    }

    @Test
    public void create_request_requires_title_and_cost() {
        boolean thrown = false;
        try {
            CreateChannelRewardRequest.builder().title("Only title").build();
        } catch (IllegalStateException exception) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
