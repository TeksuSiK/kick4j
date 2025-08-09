package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.teksusik.kick4j.categories.Category;

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

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public Boolean getHasMatureContent() {
        return hasMatureContent;
    }

    public Category getCategory() {
        return category;
    }
}
