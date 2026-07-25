package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.categories.Category;

import java.util.List;

public class LivestreamV2 {
    private final LivestreamUser broadcasterUser;
    private final Category category;
    private final LivestreamChannel channel;
    private final Boolean hasMatureContent;
    private final String id;
    private final String languageCode;
    private final String startedAt;
    private final List<String> tags;
    private final String thumbnail;
    private final String title;
    private final Integer viewerCount;

    @JsonCreator
    public LivestreamV2(@JsonProperty("broadcaster_user") LivestreamUser broadcasterUser,
                        @JsonProperty("category") Category category,
                        @JsonProperty("channel") LivestreamChannel channel,
                        @JsonProperty("has_mature_content") Boolean hasMatureContent,
                        @JsonProperty("id") String id,
                        @JsonProperty("language_code") String languageCode,
                        @JsonProperty("started_at") String startedAt,
                        @JsonProperty("tags") List<String> tags,
                        @JsonProperty("thumbnail") String thumbnail,
                        @JsonProperty("title") String title,
                        @JsonProperty("viewer_count") Integer viewerCount) {
        this.broadcasterUser = broadcasterUser;
        this.category = category;
        this.channel = channel;
        this.hasMatureContent = hasMatureContent;
        this.id = id;
        this.languageCode = languageCode;
        this.startedAt = startedAt;
        this.tags = tags;
        this.thumbnail = thumbnail;
        this.title = title;
        this.viewerCount = viewerCount;
    }

    public LivestreamUser getBroadcasterUser() {
        return broadcasterUser;
    }

    public Category getCategory() {
        return category;
    }

    public LivestreamChannel getChannel() {
        return channel;
    }

    public Boolean getHasMatureContent() {
        return hasMatureContent;
    }

    public String getId() {
        return id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }
}
