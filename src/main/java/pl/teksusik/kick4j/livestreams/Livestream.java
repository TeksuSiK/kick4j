package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.categories.Category;

import java.util.List;

public class Livestream {
    private final Integer broadcasterUserId;
    private final Category category;
    private final Integer channelId;
    private final List<String> customTags;
    private final Boolean hasMatureContent;
    private final String language;
    private final String profilePicture;
    private final String slug;
    private final String startedAt;
    private final String streamTitle;
    private final String thumbnail;
    private final Integer viewerCount;

    @JsonCreator
    public Livestream(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                      @JsonProperty("category") Category category,
                      @JsonProperty("channel_id") Integer channelId,
                      @JsonProperty("custom_tags") List<String> customTags,
                      @JsonProperty("has_mature_content") Boolean hasMatureContent,
                      @JsonProperty("language") String language,
                      @JsonProperty("profile_picture") String profilePicture,
                      @JsonProperty("slug") String slug,
                      @JsonProperty("started_at") String startedAt,
                      @JsonProperty("stream_title") String streamTitle,
                      @JsonProperty("thumbnail") String thumbnail,
                      @JsonProperty("viewer_count") Integer viewerCount) {
        this.broadcasterUserId = broadcasterUserId;
        this.category = category;
        this.channelId = channelId;
        this.customTags = customTags;
        this.hasMatureContent = hasMatureContent;
        this.language = language;
        this.profilePicture = profilePicture;
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

    public List<String> getCustomTags() {
        return customTags;
    }

    public Boolean getHasMatureContent() {
        return hasMatureContent;
    }

    public String getLanguage() {
        return language;
    }

    public String getProfilePicture() {
        return profilePicture;
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
