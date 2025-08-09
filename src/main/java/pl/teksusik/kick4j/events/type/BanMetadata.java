package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class BanMetadata {
    private final String reason;
    private final Instant createdAt;
    private final Instant expiresAt;

    @JsonCreator
    public BanMetadata(@JsonProperty("reason") String reason,
                       @JsonProperty("created_at") Instant createdAt,
                       @JsonProperty("expires_at") Instant expiresAt) {
        this.reason = reason;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getReason() {
        return reason;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
