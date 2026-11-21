package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdBreak {
    private final String adBreakId;
    private final String id;
    private final Integer remainingAdBreaks;

    @JsonCreator
    public AdBreak(@JsonProperty("ad_break_id") String adBreakId,
                   @JsonProperty("id") String id,
                   @JsonProperty("remaining_ad_breaks") Integer remainingAdBreaks) {
        this.adBreakId = adBreakId;
        this.id = id;
        this.remainingAdBreaks = remainingAdBreaks;
    }

    /**
     * Identifies this ad break in ad server requests.
     */
    public String getAdBreakId() {
        return adBreakId;
    }

    public String getId() {
        return id;
    }

    public Integer getRemainingAdBreaks() {
        return remainingAdBreaks;
    }
}
