package pl.teksusik.kick4j.events.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public class ChatMessageSentEvent extends KickEvent {
    private final String messageId;
    private final ReplyInfo repliesTo;
    private final EventUser broadcaster;
    private final EventUser sender;
    private final String content;
    private final List<Emote> emotes;
    private final Instant createdAt;

    @JsonCreator
    public ChatMessageSentEvent(@JsonProperty("message_id") String messageId,
                                @JsonProperty("replies_to") ReplyInfo repliesTo,
                                @JsonProperty("broadcaster") EventUser broadcaster,
                                @JsonProperty("sender") EventUser sender,
                                @JsonProperty("content") String content,
                                @JsonProperty("emotes") List<Emote> emotes,
                                @JsonProperty("created_at") Instant createdAt) {
        this.messageId = messageId;
        this.repliesTo = repliesTo;
        this.broadcaster = broadcaster;
        this.sender = sender;
        this.content = content;
        this.emotes = emotes;
        this.createdAt = createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public ReplyInfo getRepliesTo() {
        return repliesTo;
    }

    public EventUser getBroadcaster() {
        return broadcaster;
    }

    public EventUser getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public List<Emote> getEmotes() {
        return emotes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public static String getEventType() {
        return "chat.message.sent";
    }

    public static String getEventVersion() {
        return "1";
    }
}
