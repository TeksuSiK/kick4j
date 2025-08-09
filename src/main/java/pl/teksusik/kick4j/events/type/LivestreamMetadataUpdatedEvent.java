package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class LivestreamMetadataUpdatedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final LivestreamMetadata metadata;

    @JsonCreator
    public LivestreamMetadataUpdatedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                          @JsonProperty("metadata") LivestreamMetadata metadata) {
        this.broadcaster = broadcaster;
        this.metadata = metadata;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public LivestreamMetadata getMetadata() {
        return metadata;
    }

    public static String getEventType() {
        return "livestream.metadata.updated";
    }

    public static String getEventVersion() {
        return "1";
    }
}
