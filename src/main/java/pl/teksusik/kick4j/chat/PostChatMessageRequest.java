package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

    public Integer getBroadcastUserId() {
        return broadcastUserId;
    }

    public String getContent() {
        return content;
    }

    public String getReplyToMessageId() {
        return replyToMessageId;
    }

    public Type getType() {
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer broadcastUserId;
        private String content;
        private String replyToMessageId;
        private Type type;

        public Builder broadcastUserId(Integer broadcastUserId) {
            this.broadcastUserId = broadcastUserId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder replyToMessageId(String replyToMessageId) {
            this.replyToMessageId = replyToMessageId;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public PostChatMessageRequest build() {
            if (content == null)  {
                throw new IllegalStateException("Content is required");
            }

            if (type == null) {
                throw new IllegalStateException("Type is required");
            }

            if (type == Type.USER && broadcastUserId == null) {
                throw new IllegalStateException("BroadcastUserId is required when sending as user");
            }

            return new PostChatMessageRequest(broadcastUserId, content, replyToMessageId, type);
        }
    }

    public enum Type {
        USER, BOT;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
