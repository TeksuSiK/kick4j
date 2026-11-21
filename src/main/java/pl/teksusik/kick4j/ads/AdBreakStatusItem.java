package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AdBreakStatusItem {
    private final String adBreakId;
    private final Integer breakDurationSeconds;
    private final Instant createdAt;
    private final String id;
    private final String source;
    private final String status;

    @JsonCreator
    public AdBreakStatusItem(@JsonProperty("ad_break_id") String adBreakId,
                             @JsonProperty("break_duration_seconds") Integer breakDurationSeconds,
                             @JsonProperty("created_at") Instant createdAt,
                             @JsonProperty("id") String id,
                             @JsonProperty("source") String source,
                             @JsonProperty("status") String status) {
        this.adBreakId = adBreakId;
        this.breakDurationSeconds = breakDurationSeconds;
        this.createdAt = createdAt;
        this.id = id;
        this.source = source;
        this.status = status;
    }

    /**
     * Identifies this ad break in ad server requests. {@code null} until the ad break is accepted.
     */
    public String getAdBreakId() {
        return adBreakId;
    }

    public Integer getBreakDurationSeconds() {
        return breakDurationSeconds;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    /**
     * Where the ad break came from, e.g. {@code public_api}.
     */
    public String getSource() {
        return source;
    }

    /**
     * Ad break state, e.g. {@code inserted}.
     */
    public String getStatus() {
        return status;
    }
}
