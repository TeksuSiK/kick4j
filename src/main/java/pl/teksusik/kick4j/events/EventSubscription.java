package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventSubscription {
    private final String aapId;
    private final Integer broadcasterUserId;
    private final String createdAt;
    private final String event;
    private final String id;
    private final String method;
    private final String updatedAt;
    private final Integer version;

    @JsonCreator
    public EventSubscription(@JsonProperty("app_id") String aapId,
                             @JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                             @JsonProperty("created_at") String createdAt,
                             @JsonProperty("event") String event,
                             @JsonProperty("id") String id,
                             @JsonProperty("method") String method,
                             @JsonProperty("updated_at") String updatedAt,
                             @JsonProperty("version") Integer version) {
        this.aapId = aapId;
        this.broadcasterUserId = broadcasterUserId;
        this.createdAt = createdAt;
        this.event = event;
        this.id = id;
        this.method = method;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public String getAapId() {
        return aapId;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEvent() {
        return event;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getVersion() {
        return version;
    }
}
