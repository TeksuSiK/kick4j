package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Gift {
    private final int amount;
    private final String name;
    private final String type;
    private final String tier;
    private final String message;

    @JsonCreator
    public Gift(@JsonProperty("amount") int amount,
                @JsonProperty("name") String name,
                @JsonProperty("type") String type,
                @JsonProperty("tier") String tier,
                @JsonProperty("message") String message) {
        this.amount = amount;
        this.name = name;
        this.type = type;
        this.tier = tier;
        this.message = message;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getTier() {
        return tier;
    }

    public String getMessage() {
        return message;
    }
}
