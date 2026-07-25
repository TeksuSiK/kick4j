package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LivestreamChannel {
    private final String slug;

    @JsonCreator
    public LivestreamChannel(@JsonProperty("slug") String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }
}
