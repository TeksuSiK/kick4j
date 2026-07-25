package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RedemptionStatus {
    PENDING, ACCEPTED, REJECTED;

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static RedemptionStatus fromValue(String value) {
        return valueOf(value.toUpperCase());
    }
}
