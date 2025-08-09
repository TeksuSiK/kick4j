package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

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

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Method getMethod() {
        return method;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer broadcasterUserId;
        private final List<Event> events = new ArrayList<>();
        private Method method;

        public Builder broadcasterUserId(Integer broadcasterUserId) {
            this.broadcasterUserId = broadcasterUserId;
            return this;
        }

        public Builder addEvent(Event event) {
            this.events.add(event);
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public EventSubscriptionRequest build() {
            return new EventSubscriptionRequest(broadcasterUserId, events, method);
        }
    }

    public static class Event {
        private final String name;
        private final Integer version;

        public Event(String name, Integer version) {
            this.name = name;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public Integer getVersion() {
            return version;
        }
    }

    public enum Method {
        WEBHOOK;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
