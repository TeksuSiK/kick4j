package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostChatMessageResponse {
    private final Boolean isSent;
    private final String messageId;

    @JsonCreator
    public PostChatMessageResponse(@JsonProperty("is_sent") Boolean isSent,
                                   @JsonProperty("message_id") String messageId) {
        this.isSent = isSent;
        this.messageId = messageId;
    }

    public Boolean getSent() {
        return isSent;
    }

    public String getMessageId() {
        return messageId;
    }
}
