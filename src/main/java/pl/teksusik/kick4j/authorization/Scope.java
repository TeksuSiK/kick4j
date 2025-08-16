package pl.teksusik.kick4j.authorization;

import lombok.Getter;

@Getter
public enum Scope {
    USER_READ("user:read"),
    CHANNEL_READ("channel:read"),
    CHANNEL_WRITE("channel:write"),
    CHAT_WRITE("chat:write"),
    STREAMKEY_READ("streamkey:read"),
    EVENTS_SUBSCRIBE("events:subscribe"),
    MODERATION_BAN("moderation:ban");

    private final String scope;

    Scope(String scope) {
        this.scope = scope;
    }
}
