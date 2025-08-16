package pl.teksusik.kick4j.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteModerationBansRequest {
    private final Integer broadcasterUserId;
    private final Integer userId;

    public DeleteModerationBansRequest(@JsonProperty("broadcaster_user_id") Integer broadcasterUserId,
                                       @JsonProperty("user_id") Integer userId) {
        this.broadcasterUserId = broadcasterUserId;
        this.userId = userId;
    }
}
