package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class BanMetadata {
    private final String reason;
    private final Instant createdAt;
    @Nullable
    private final Instant expiresAt;

    @JsonCreator
    public BanMetadata(@JsonProperty("reason") String reason,
                       @JsonProperty("created_at") Instant createdAt,
                       @Nullable @JsonProperty("expires_at") Instant expiresAt) {
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

    @Nullable
    public Instant getExpiresAt() {
        return expiresAt;
    }
}
