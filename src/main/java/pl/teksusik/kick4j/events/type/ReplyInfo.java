package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyInfo {
    private final String messageId;
    private final String content;
    private final EventUser sender;

    @JsonCreator
    public ReplyInfo(@JsonProperty("message_id") String messageId,
                     @JsonProperty("content") String content,
                     @JsonProperty("sender") EventUser sender) {
        this.messageId = messageId;
        this.content = content;
        this.sender = sender;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public EventUser getSender() {
        return sender;
    }
}
