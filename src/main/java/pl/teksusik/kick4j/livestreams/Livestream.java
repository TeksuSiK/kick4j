package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.categories.Category;

public class Livestream {
    private final Integer broadcasterUserId;
    private final Category category;
    private final Integer channelId;
    private final Boolean hasMatureContent;
    private final String language;
    private final String slug;
    private final String startedAt;
    private final String streamTitle;
    private final String thumbnail;
    private final Integer viewerCount;

    @JsonCreator
    public Livestream(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                      @JsonProperty("category") Category category,
                      @JsonProperty("channel_id") Integer channelId,
                      @JsonProperty("has_mature_content") Boolean hasMatureContent,
                      @JsonProperty("language") String language,
                      @JsonProperty("slug") String slug,
                      @JsonProperty("started_at") String startedAt,
                      @JsonProperty("stream_title") String streamTitle,
                      @JsonProperty("thumbnail") String thumbnail,
                      @JsonProperty("viewer_count") Integer viewerCount) {
        this.broadcasterUserId = broadcasterUserId;
        this.category = category;
        this.channelId = channelId;
        this.hasMatureContent = hasMatureContent;
        this.language = language;
        this.slug = slug;
        this.startedAt = startedAt;
        this.streamTitle = streamTitle;
        this.thumbnail = thumbnail;
        this.viewerCount = viewerCount;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public Boolean getHasMatureContent() {
        return hasMatureContent;
    }

    public String getLanguage() {
        return language;
    }

    public String getSlug() {
        return slug;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }
}
