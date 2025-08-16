package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class ChannelSubscriptionGiftsEvent extends KickEvent {
    private final EventUser broadcaster;
    private final EventUser subscriber;
    private final List<EventUser> giftees;
    private final Instant createdAt;
    private final Instant expiresAt;

    @JsonCreator
    public ChannelSubscriptionGiftsEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                         @JsonProperty("subscriber") EventUser subscriber,
                                         @JsonProperty("giftees") List<EventUser> giftees,
                                         @JsonProperty("created_at") Instant createdAt,
                                         @JsonProperty("expires_at") Instant expiresAt) {
        this.broadcaster = broadcaster;
        this.subscriber = subscriber;
        this.giftees = giftees;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public static String getEventType() {
        return "channel.subscription.gifts";
    }

    public static String getEventVersion() {
        return "1";
    }
}
