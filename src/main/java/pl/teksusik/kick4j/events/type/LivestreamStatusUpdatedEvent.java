package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.Instant;

@Getter
public class LivestreamStatusUpdatedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final Boolean isLive;
    private final String title;
    private final Instant startedAt;
    private final Instant endedAt;

    @JsonCreator
    public LivestreamStatusUpdatedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                        @JsonProperty("is_live") Boolean isLive,
                                        @JsonProperty("title") String title,
                                        @JsonProperty("started_at") Instant startedAt,
                                        @JsonProperty("ended_at") Instant endedAt) {
        this.broadcaster = broadcaster;
        this.isLive = isLive;
        this.title = title;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public static String getEventType() {
        return "livestream.status.updated";
    }

    public static String getEventVersion() {
        return "1";
    }
}
