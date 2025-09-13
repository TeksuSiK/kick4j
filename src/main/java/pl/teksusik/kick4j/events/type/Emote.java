package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Emote {
    private final String emoteId;
    private final List<Position> positions;

    @JsonCreator
    public Emote(@JsonProperty("emote_id") String emoteId,
                 @JsonProperty("positions") List<Position> positions) {
        this.emoteId = emoteId;
        this.positions = positions;
    }

    public String getEmoteId() {
        return emoteId;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public static class Position {
        private final String start;
        private final String end;

        @JsonCreator
        public Position(@JsonProperty("s") String start,
                        @JsonProperty("e") String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }
}
