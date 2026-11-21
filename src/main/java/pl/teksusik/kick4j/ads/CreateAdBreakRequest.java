package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Body of {@code POST /ads/ad-break}. Both fields are required.
 */
public class CreateAdBreakRequest {
    @JsonProperty("break_duration_seconds")
    private final Integer breakDurationSeconds;
    @JsonProperty("id")
    private final String id;

    /**
     * @param breakDurationSeconds ad break duration in seconds, between 7 and 90
     * @param id                   client-generated UUID for the ad break; the API echoes it back
     *                             as {@link AdBreak#getId()} and in the ad break status
     */
    public CreateAdBreakRequest(Integer breakDurationSeconds, String id) {
        this.breakDurationSeconds = breakDurationSeconds;
        this.id = id;
    }

    public Integer getBreakDurationSeconds() {
        return breakDurationSeconds;
    }

    public String getId() {
        return id;
    }
}
