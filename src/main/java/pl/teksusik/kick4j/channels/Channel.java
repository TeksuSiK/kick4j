package pl.teksusik.kick4j.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pl.teksusik.kick4j.categories.Category;

@Getter
public class Channel {
    private final String bannerPicture;
    private final Integer broadcasterUserId;
    private final Category category;
    private final String channelDescription;
    private final String slug;
    private final StreamInformation stream;
    private final String streamTitle;

    @JsonCreator
    public Channel(@JsonProperty("banner_picture") String bannerPicture,
                   @JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                   @JsonProperty("category") Category category,
                   @JsonProperty("channel_description") String channelDescription,
                   @JsonProperty("slug") String slug,
                   @JsonProperty("stream") StreamInformation stream,
                   @JsonProperty("stream_title") String streamTitle) {
        this.bannerPicture = bannerPicture;
        this.broadcasterUserId = broadcasterUserId;
        this.category = category;
        this.channelDescription = channelDescription;
        this.slug = slug;
        this.stream = stream;
        this.streamTitle = streamTitle;
    }
}
