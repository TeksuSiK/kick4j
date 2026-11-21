package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdBreakLimits {
    private final Integer max;
    private final Integer periodSeconds;

    @JsonCreator
    public AdBreakLimits(@JsonProperty("max") Integer max,
                         @JsonProperty("period_seconds") Integer periodSeconds) {
        this.max = max;
        this.periodSeconds = periodSeconds;
    }

    /**
     * Maximum number of ad breaks allowed within {@link #getPeriodSeconds()}.
     */
    public Integer getMax() {
        return max;
    }

    public Integer getPeriodSeconds() {
        return periodSeconds;
    }
}
