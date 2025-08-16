package pl.teksusik.kick4j.moderation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostModerationBansRequest {
    private final Integer broadcasterUserId;
    private final Integer duration;
    private final String reason;
    private final Integer userId;

    @JsonCreator
    public PostModerationBansRequest(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                                     @JsonProperty("duration") Integer duration,
                                     @JsonProperty("reason") String reason,
                                     @JsonProperty("user_id") Integer userId) {
        this.broadcasterUserId = broadcasterUserId;
        this.duration = duration;
        this.reason = reason;
        this.userId = userId;
    }
}
