package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChannelFollowedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final EventUser follower;

    @JsonCreator
    public ChannelFollowedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                @JsonProperty("follower") EventUser follower) {
        this.broadcaster = broadcaster;
        this.follower = follower;
    }

    public static String getEventType() {
        return "channel.followed";
    }

    public static String getEventVersion() {
        return "1";
    }
}
