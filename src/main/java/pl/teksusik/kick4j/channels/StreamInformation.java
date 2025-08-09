package pl.teksusik.kick4j.channels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamInformation {
    private final Boolean isLive;
    private final Boolean isMature;
    private final String key;
    private final String language;
    private final String startTime;
    private final String thumbnail;
    private final String url;
    private final Integer viewerCount;

    @JsonCreator
    public StreamInformation(@JsonProperty("is_live") Boolean isLive,
                             @JsonProperty("is_mature") Boolean isMature,
                             @JsonProperty("key") String key,
                             @JsonProperty("language") String language,
                             @JsonProperty("start_time") String startTime,
                             @JsonProperty("thumbnail") String thumbnail,
                             @JsonProperty("url") String url,
                             @JsonProperty("viewer_count") Integer viewerCount) {
        this.isLive = isLive;
        this.isMature = isMature;
        this.key = key;
        this.language = language;
        this.startTime = startTime;
        this.thumbnail = thumbnail;
        this.url = url;
        this.viewerCount = viewerCount;
    }

    public Boolean isLive() {
        return isLive;
    }

    public Boolean isMature() {
        return isMature;
    }

    public String getKey() {
        return key;
    }

    public String getLanguage() {
        return language;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }
}
