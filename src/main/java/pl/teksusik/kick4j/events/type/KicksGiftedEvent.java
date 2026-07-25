package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class KicksGiftedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final Instant createdAt;
    private final KicksGift gift;
    private final EventUser sender;

    @JsonCreator
    public KicksGiftedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                            @JsonProperty("created_at") Instant createdAt,
                            @JsonProperty("gift") KicksGift gift,
                            @JsonProperty("sender") EventUser sender) {
        this.broadcaster = broadcaster;
        this.createdAt = createdAt;
        this.gift = gift;
        this.sender = sender;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public KicksGift getGift() {
        return gift;
    }

    public EventUser getSender() {
        return sender;
    }

    public static String getEventType() {
        return "kicks.gifted";
    }

    public static String getEventVersion() {
        return "1";
    }
}
