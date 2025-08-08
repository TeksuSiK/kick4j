package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    private final int id;
    private final String name;
    private final String thumbnail;

    @JsonCreator
    public Category(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("thumbnail") String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
