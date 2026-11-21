package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.api.PaginatedResponse;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Verifies that the redemptions response is deserialized with its pagination cursor.
 */
public class RedemptionsPageDeserializationTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final String PAYLOAD = """
            {
              "data": [
                {
                  "redemptions": [
                    {
                      "id": "01HZX2Y3Z4A5B6C7D8E9F0G1H2",
                      "redeemed_at": "2025-06-01T12:00:00Z",
                      "redeemer": { "user_id": 123 },
                      "status": "pending",
                      "user_input": "https://example.com/song"
                    }
                  ],
                  "reward": {
                    "can_manage": true,
                    "cost": 100,
                    "description": "Request a song",
                    "id": "01HZREWARD0000000000000000",
                    "is_deleted": false,
                    "title": "Song Request"
                  }
                }
              ],
              "message": "OK",
              "pagination": { "next_cursor": "eyJpZCI6IjAxSFoifQ" }
            }
            """;

    @Test
    public void exposes_next_cursor_alongside_data() throws Exception {
        PaginatedResponse<RedemptionsByReward> page = MAPPER.readValue(PAYLOAD, new TypeReference<>() {});

        assertEquals("OK", page.getMessage());
        assertEquals("eyJpZCI6IjAxSFoifQ", page.getPagination().getNextCursor());

        List<RedemptionsByReward> data = page.getData();
        assertEquals(1, data.size());
        assertEquals("Song Request", data.get(0).getReward().getTitle());

        ChannelRewardRedemption redemption = data.get(0).getRedemptions().get(0);
        assertEquals("01HZX2Y3Z4A5B6C7D8E9F0G1H2", redemption.getId());
        assertEquals(Instant.parse("2025-06-01T12:00:00Z"), redemption.getRedeemedAt());
        assertEquals(RedemptionStatus.PENDING, redemption.getStatus());
        assertEquals(123L, redemption.getRedeemer().getUserId());
    }

    @Test
    public void last_page_has_null_next_cursor() throws Exception {
        PaginatedResponse<RedemptionsByReward> page = MAPPER.readValue(
                "{\"data\": [], \"message\": \"OK\", \"pagination\": {\"next_cursor\": null}}",
                new TypeReference<>() {});

        assertEquals(0, page.getData().size());
        assertNull(page.getPagination().getNextCursor());
    }
}
