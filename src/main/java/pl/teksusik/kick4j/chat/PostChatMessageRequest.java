package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostChatMessageRequest {
    private final Integer broadcastUserId;
    private final String content;
    private final String replyToMessageId;
    private final Type type;

    @JsonCreator
    public PostChatMessageRequest(@JsonProperty("broadcast_user_id") Integer broadcastUserId,
                                  @JsonProperty("conent") String content,
                                  @JsonProperty("reply_to_message_id") String replyToMessageId,
                                  @JsonProperty("type") Type type) {
        this.broadcastUserId = broadcastUserId;
        this.content = content;
        this.replyToMessageId = replyToMessageId;
        this.type = type;
    }

    public enum Type {
        USER, BOT;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
