package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Emote {
    private final String emoteId;
    private final List<Position> positions;

    @JsonCreator
    public Emote(@JsonProperty("emote_id") String emoteId,
                 @JsonProperty("positions") List<Position> positions) {
        this.emoteId = emoteId;
        this.positions = positions;
    }

    @Getter
    public static class Position {
        private final String start;
        private final String end;

        @JsonCreator
        public Position(@JsonProperty("s") String start,
                        @JsonProperty("e") String end) {
            this.start = start;
            this.end = end;
        }
    }
}
