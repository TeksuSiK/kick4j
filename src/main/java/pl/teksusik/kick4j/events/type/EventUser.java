package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventUser {
    private final Boolean isAnonymous;
    private final Integer userId;
    private final String username;
    private final Boolean isVerified;
    private final String profilePicture;
    private final String channelSlug;
    private final Identity identity;

    @JsonCreator
    public EventUser(@JsonProperty("is_anonymous") Boolean isAnonymous,
                     @JsonProperty("user_id") Integer userId,
                     @JsonProperty("username") String username,
                     @JsonProperty("is_verified") Boolean isVerified,
                     @JsonProperty("profile_picture") String profilePicture,
                     @JsonProperty("channel_slug") String channelSlug,
                     @JsonProperty("identity") Identity identity) {
        this.isAnonymous = isAnonymous;
        this.userId = userId;
        this.username = username;
        this.isVerified = isVerified;
        this.profilePicture = profilePicture;
        this.channelSlug = channelSlug;
        this.identity = identity;
    }
}
