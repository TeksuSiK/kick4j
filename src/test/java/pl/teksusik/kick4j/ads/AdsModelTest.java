package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.api.ApiResponse;
import pl.teksusik.kick4j.api.ApiResponseDeserializer;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdsModelTest {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new SimpleModule().addDeserializer(ApiResponse.class, new ApiResponseDeserializer<>()));

    @Test
    public void ad_break_status_deserializes_from_envelope() throws Exception {
        String payload = """
                {
                  "data": {
                    "ad_breaks": [
                      {
                        "ad_break_id": "Xq3LmP9aB2cD",
                        "break_duration_seconds": 30,
                        "created_at": "2026-01-02T03:04:05Z",
                        "id": "123e4567-e89b-12d3-a456-426614174000",
                        "source": "public_api",
                        "status": "inserted"
                      },
                      {
                        "break_duration_seconds": 60,
                        "created_at": "2026-01-02T03:34:05Z",
                        "id": "9b2f6c1e-3d4a-4f5b-8c7d-0e1f2a3b4c5d",
                        "source": "public_api",
                        "status": "pending"
                      }
                    ],
                    "ads_blocked": false,
                    "auto_ads_active": false,
                    "limits": {
                      "max": 3,
                      "period_seconds": 3600
                    },
                    "opted_in": true,
                    "remaining_ad_breaks": 1
                  },
                  "message": "OK"
                }
                """;

        AdBreakStatus status = MAPPER.readValue(payload, new TypeReference<ApiResponse<AdBreakStatus>>() {}).getData();

        assertFalse(status.getAdsBlocked());
        assertFalse(status.getAutoAdsActive());
        assertTrue(status.getOptedIn());
        assertEquals(1, status.getRemainingAdBreaks());
        assertEquals(3, status.getLimits().getMax());
        assertEquals(3600, status.getLimits().getPeriodSeconds());
        assertEquals(2, status.getAdBreaks().size());

        AdBreakStatusItem accepted = status.getAdBreaks().get(0);
        assertEquals("Xq3LmP9aB2cD", accepted.getAdBreakId());
        assertEquals(30, accepted.getBreakDurationSeconds());
        assertEquals(Instant.parse("2026-01-02T03:04:05Z"), accepted.getCreatedAt());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", accepted.getId());
        assertEquals("public_api", accepted.getSource());
        assertEquals("inserted", accepted.getStatus());

        // ad_break_id is missing until the ad break is accepted.
        assertNull(status.getAdBreaks().get(1).getAdBreakId());
    }

    @Test
    public void create_ad_break_response_deserializes_from_envelope() throws Exception {
        String payload = """
                {
                  "data": {
                    "ad_break_id": "Xq3LmP9aB2cD",
                    "id": "123e4567-e89b-12d3-a456-426614174000",
                    "remaining_ad_breaks": 2
                  },
                  "message": "Accepted"
                }
                """;

        AdBreak adBreak = MAPPER.readValue(payload, new TypeReference<ApiResponse<AdBreak>>() {}).getData();

        assertEquals("Xq3LmP9aB2cD", adBreak.getAdBreakId());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", adBreak.getId());
        assertEquals(2, adBreak.getRemainingAdBreaks());
    }

    @Test
    public void create_ad_break_request_serializes_snake_case_keys() {
        CreateAdBreakRequest request = new CreateAdBreakRequest(30, "123e4567-e89b-12d3-a456-426614174000");

        Map<String, Object> json = MAPPER.convertValue(request, new TypeReference<>() {});

        assertEquals(Map.of(
                "break_duration_seconds", 30,
                "id", "123e4567-e89b-12d3-a456-426614174000"), json);
    }
}
