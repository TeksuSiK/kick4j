package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Badge {
    private final String text;
    private final String type;
    private final Integer count;

    @JsonCreator
    public Badge(@JsonProperty("text") String text,
                 @JsonProperty("type") String type,
                 @JsonProperty("count") Integer count) {
        this.text = text;
        this.type = type;
        this.count = count;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public Integer getCount() {
        return count;
    }
}
