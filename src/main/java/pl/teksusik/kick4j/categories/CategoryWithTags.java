package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CategoryWithTags {
    private final Integer id;
    private final String name;
    private final List<String> tags;
    private final String thumbnail;

    @JsonCreator
    public CategoryWithTags(@JsonProperty("id") Integer id,
                            @JsonProperty("name") String name,
                            @JsonProperty("tags") List<String> tags,
                            @JsonProperty("thumbnail") String thumbnail) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.thumbnail = thumbnail;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
