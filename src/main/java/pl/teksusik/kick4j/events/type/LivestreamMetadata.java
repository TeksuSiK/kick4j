package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pl.teksusik.kick4j.categories.Category;

@Getter
public class LivestreamMetadata {
    private final String title;
    private final String language;
    private final Boolean hasMatureContent;
    private final Category category;

    public LivestreamMetadata(@JsonProperty("title") String title,
                              @JsonProperty("language") String language,
                              @JsonProperty("has_mature_content") Boolean hasMatureContent,
                              @JsonProperty("category") Category category) {
        this.title = title;
        this.language = language;
        this.hasMatureContent = hasMatureContent;
        this.category = category;
    }
}
