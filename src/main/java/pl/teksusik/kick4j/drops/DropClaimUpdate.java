package pl.teksusik.kick4j.drops;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A single claim status update for {@code PATCH /drops/claims}.
 */
public class DropClaimUpdate {
    @JsonProperty("claim_id")
    private final String claimId;
    @JsonProperty("external_status")
    private final String externalStatus;

    public DropClaimUpdate(String claimId, String externalStatus) {
        this.claimId = claimId;
        this.externalStatus = externalStatus;
    }

    public String getClaimId() {
        return claimId;
    }

    public String getExternalStatus() {
        return externalStatus;
    }
}
