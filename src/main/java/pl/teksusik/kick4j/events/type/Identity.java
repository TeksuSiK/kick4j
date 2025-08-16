package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Identity {
    private final String usernameColor;
    private final List<Badge> badges;

    @JsonCreator
    public Identity(@JsonProperty("username_color") String usernameColor,
                    @JsonProperty("badges") List<Badge> badges) {
        this.usernameColor = usernameColor;
        this.badges = badges;
    }
}
