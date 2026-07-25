package pl.teksusik.kick4j.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.categories.Category;

public class Channel {
    private final Integer activeSubscribersCount;
    private final String bannerPicture;
    private final Integer broadcasterUserId;
    private final Integer canceledSubscribersCount;
    private final Category category;
    private final String channelDescription;
    private final String slug;
    private final StreamInformation stream;
    private final String streamTitle;

    @JsonCreator
    public Channel(@JsonProperty("active_subscribers_count") Integer activeSubscribersCount,
                   @JsonProperty("banner_picture") String bannerPicture,
                   @JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                   @JsonProperty("canceled_subscribers_count") Integer canceledSubscribersCount,
                   @JsonProperty("category") Category category,
                   @JsonProperty("channel_description") String channelDescription,
                   @JsonProperty("slug") String slug,
                   @JsonProperty("stream") StreamInformation stream,
                   @JsonProperty("stream_title") String streamTitle) {
        this.activeSubscribersCount = activeSubscribersCount;
        this.bannerPicture = bannerPicture;
        this.broadcasterUserId = broadcasterUserId;
        this.canceledSubscribersCount = canceledSubscribersCount;
        this.category = category;
        this.channelDescription = channelDescription;
        this.slug = slug;
        this.stream = stream;
        this.streamTitle = streamTitle;
    }

    public Integer getActiveSubscribersCount() {
        return activeSubscribersCount;
    }

    public Integer getCanceledSubscribersCount() {
        return canceledSubscribersCount;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public Category getCategory() {
        return category;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public String getSlug() {
        return slug;
    }

    public StreamInformation getStream() {
        return stream;
    }

    public String getStreamTitle() {
        return streamTitle;
    }
}
