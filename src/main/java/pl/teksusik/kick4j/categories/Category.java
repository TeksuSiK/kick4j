package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Category {
    private final Integer id;
    private final String name;
    private final String thumbnail;

    @JsonCreator
    public Category(@JsonProperty("id") Integer id, @JsonProperty("name") String name, @JsonProperty("thumbnail") String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }
}
