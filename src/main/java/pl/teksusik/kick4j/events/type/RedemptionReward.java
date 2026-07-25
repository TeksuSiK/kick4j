package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedemptionReward {
    private final Integer cost;
    private final String description;
    private final String id;
    private final String title;

    @JsonCreator
    public RedemptionReward(@JsonProperty("cost") Integer cost,
                            @JsonProperty("description") String description,
                            @JsonProperty("id") String id,
                            @JsonProperty("title") String title) {
        this.cost = cost;
        this.description = description;
        this.id = id;
        this.title = title;
    }

    public Integer getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
