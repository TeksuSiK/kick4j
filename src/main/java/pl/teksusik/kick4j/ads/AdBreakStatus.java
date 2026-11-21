package pl.teksusik.kick4j.ads;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AdBreakStatus {
    private final List<AdBreakStatusItem> adBreaks;
    private final Boolean adsBlocked;
    private final Boolean autoAdsActive;
    private final AdBreakLimits limits;
    private final Boolean optedIn;
    private final Integer remainingAdBreaks;

    @JsonCreator
    public AdBreakStatus(@JsonProperty("ad_breaks") List<AdBreakStatusItem> adBreaks,
                         @JsonProperty("ads_blocked") Boolean adsBlocked,
                         @JsonProperty("auto_ads_active") Boolean autoAdsActive,
                         @JsonProperty("limits") AdBreakLimits limits,
                         @JsonProperty("opted_in") Boolean optedIn,
                         @JsonProperty("remaining_ad_breaks") Integer remainingAdBreaks) {
        this.adBreaks = adBreaks;
        this.adsBlocked = adsBlocked;
        this.autoAdsActive = autoAdsActive;
        this.limits = limits;
        this.optedIn = optedIn;
        this.remainingAdBreaks = remainingAdBreaks;
    }

    public List<AdBreakStatusItem> getAdBreaks() {
        return adBreaks;
    }

    public Boolean getAdsBlocked() {
        return adsBlocked;
    }

    public Boolean getAutoAdsActive() {
        return autoAdsActive;
    }

    public AdBreakLimits getLimits() {
        return limits;
    }

    public Boolean getOptedIn() {
        return optedIn;
    }

    public Integer getRemainingAdBreaks() {
        return remainingAdBreaks;
    }
}
