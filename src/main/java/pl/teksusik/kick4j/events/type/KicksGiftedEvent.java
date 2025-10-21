package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class KicksGiftedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final EventUser sender;
    private final Gift gift;
    private final Instant createdAt;

    @JsonCreator
    public KicksGiftedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                            @JsonProperty("sender") EventUser sender,
                            @JsonProperty("gift") Gift gift,
                            @JsonProperty("created_at") Instant createdAt) {
        this.broadcaster = broadcaster;
        this.sender = sender;
        this.gift = gift;
        this.createdAt = createdAt;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public EventUser getSender() {
        return sender;
    }

    public Gift getGift() {
        return gift;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public static String getEventType() {
        return "kicks.gifted";
    }

    public static String getEventVersion() {
        return "1";
    }
}
