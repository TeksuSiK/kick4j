package pl.teksusik.kick4j.kicks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class KicksLeaderboard {
    private final List<KicksLeaderboardEntry> lifetime;
    private final List<KicksLeaderboardEntry> month;
    private final List<KicksLeaderboardEntry> week;

    @JsonCreator
    public KicksLeaderboard(@JsonProperty("lifetime") List<KicksLeaderboardEntry> lifetime,
                            @JsonProperty("month") List<KicksLeaderboardEntry> month,
                            @JsonProperty("week") List<KicksLeaderboardEntry> week) {
        this.lifetime = lifetime;
        this.month = month;
        this.week = week;
    }

    public List<KicksLeaderboardEntry> getLifetime() {
        return lifetime;
    }

    public List<KicksLeaderboardEntry> getMonth() {
        return month;
    }

    public List<KicksLeaderboardEntry> getWeek() {
        return week;
    }
}
