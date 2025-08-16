package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventSubscriptionRequest {
    private final Integer broadcasterUserId;
    private final List<Event> events;
    private final Method method;

    @JsonCreator
    public EventSubscriptionRequest(@JsonProperty("broadcast_user_id") Integer broadcasterUserId,
                                    @JsonProperty("events") List<Event> events,
                                    @JsonProperty("method") Method method) {
        this.broadcasterUserId = broadcasterUserId;
        this.events = events;
        this.method = method;
    }

    @Getter
    @AllArgsConstructor
    public static class Event {
        private final String name;
        private final Integer version;
    }

    public enum Method {
        WEBHOOK;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
