package pl.teksusik.kick4j.rewards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MinimalChannelReward {
    private final Boolean canManage;
    private final Long cost;
    private final String description;
    private final String id;
    private final Boolean isDeleted;
    private final String title;

    @JsonCreator
    public MinimalChannelReward(@JsonProperty("can_manage") Boolean canManage,
                                @JsonProperty("cost") Long cost,
                                @JsonProperty("description") String description,
                                @JsonProperty("id") String id,
                                @JsonProperty("is_deleted") Boolean isDeleted,
                                @JsonProperty("title") String title) {
        this.canManage = canManage;
        this.cost = cost;
        this.description = description;
        this.id = id;
        this.isDeleted = isDeleted;
        this.title = title;
    }

    public Boolean getCanManage() {
        return canManage;
    }

    public Long getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public String getTitle() {
        return title;
    }
}
