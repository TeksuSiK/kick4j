package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;

@Getter
public class ChannelSubscriptionRenewalEvent extends KickEvent {
    private final EventUser broadcaster;
    private final EventUser subscriber;
    private final Integer duration;
    private final Instant createdAt;
    private final Instant expiresAt;

    @JsonCreator
    public ChannelSubscriptionRenewalEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                           @JsonProperty("subscriber") EventUser subscriber,
                                           @JsonProperty("duration") Integer duration,
                                           @JsonProperty("created_at") Instant createdAt,
                                           @JsonProperty("expires_at") Instant expiresAt) {
        this.broadcaster = broadcaster;
        this.subscriber = subscriber;
        this.duration = duration;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static String getEventType() {
        return "channel.subscription.renewal";
    }

    public static String getEventVersion() {
        return "1";
    }
}
