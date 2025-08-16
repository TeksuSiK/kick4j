package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EventSubscriptionResponse {
    private final String error;
    private final String name;
    private final String subscriptionId;
    private final Integer version;

    @JsonCreator
    public EventSubscriptionResponse(@JsonProperty("error") String error,
                                     @JsonProperty("name") String name,
                                     @JsonProperty("subscription_id") String subscriptionId,
                                     @JsonProperty("version") Integer version) {
        this.error = error;
        this.name = name;
        this.subscriptionId = subscriptionId;
        this.version = version;
    }
}
