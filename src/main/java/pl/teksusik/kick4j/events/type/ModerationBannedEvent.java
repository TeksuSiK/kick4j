package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationBannedEvent extends KickEvent {
    private final EventUser broadcaster;
    private final EventUser moderator;
    private final EventUser bannedUser;
    private final BanMetadata metadata;

    @JsonCreator
    public ModerationBannedEvent(@JsonProperty("broadcaster") EventUser broadcaster,
                                 @JsonProperty("moderator") EventUser moderator,
                                 @JsonProperty("banned_user") EventUser bannedUser,
                                 @JsonProperty("metadata") BanMetadata metadata) {
        this.broadcaster = broadcaster;
        this.moderator = moderator;
        this.bannedUser = bannedUser;
        this.metadata = metadata;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public EventUser getModerator() {
        return moderator;
    }

    public EventUser getBannedUser() {
        return bannedUser;
    }

    public BanMetadata getMetadata() {
        return metadata;
    }

    public static String getEventType() {
        return "moderation.banned";
    }

    public static String getEventVersion() {
        return "1";
    }
}
