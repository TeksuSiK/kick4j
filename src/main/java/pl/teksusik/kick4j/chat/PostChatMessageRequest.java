package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostChatMessageRequest {
    private final Integer broadcasterUserId;
    private final String content;
    private final String replyToMessageId;
    private final Type type;

    public PostChatMessageRequest(Integer broadcasterUserId, String content, String replyToMessageId, Type type) {
        this.broadcasterUserId = broadcasterUserId;
        this.content = content;
        this.replyToMessageId = replyToMessageId;
        this.type = type;
    }

    public Integer getBroadcasterUserId() {
        return broadcasterUserId;
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
        private Integer broadcasterUserId;
        private String content;
        private String replyToMessageId;
        private Type type;

        public Builder broadcastUserId(Integer broadcastUserId) {
            this.broadcasterUserId = broadcastUserId;
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

            if (type == Type.USER && broadcasterUserId == null) {
                throw new IllegalStateException("BroadcastUserId is required when sending as user");
            }

            return new PostChatMessageRequest(broadcasterUserId, content, replyToMessageId, type);
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
