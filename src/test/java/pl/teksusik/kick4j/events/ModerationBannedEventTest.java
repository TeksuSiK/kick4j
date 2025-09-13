package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.BanMetadata;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.ModerationBannedEvent;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModerationBannedEventTest extends KickEventDispatcherTest {
    private static final String PAYLOAD = """
            {
              "broadcaster": {
                "is_anonymous": false,
                "user_id": 123456789,
                "username": "broadcaster_name",
                "is_verified": true,
                "profile_picture": "https://example.com/broadcaster_avatar.jpg",
                "channel_slug": "broadcaster_channel",
                "identity": null
              },
              "moderator": {
                "is_anonymous": false,
                "user_id": 987654321,
                "username": "moderator_name",
                "is_verified": false,
                "profile_picture": "https://example.com/moderator_avatar.jpg",
                "channel_slug": "moderator_channel",
                "identity": null
              },
              "banned_user": {
                "is_anonymous": false,
                "user_id": 135790135,
                "username": "banned_user_name",
                "is_verified": false,
                "profile_picture": "https://example.com/banned_user_avatar.jpg",
                "channel_slug": "banned_user_channel",
                "identity": null
              },
              "metadata": {
                "reason": "banned reason",
                "created_at": "2025-01-14T16:08:05Z",
                "expires_at": "2025-01-14T16:10:06Z"
              }
            }""";

    @Test
    public void should_deserialize_moderation_banned_event() {
        ModerationBannedEvent event = dispatchAndCapture(
                ModerationBannedEvent.class,
                "moderation.banned",
                "1",
                PAYLOAD
        );

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertFalse(broadcaster.getAnonymous());
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());

        EventUser moderator = event.getModerator();
        assertNotNull(moderator);
        assertEquals(987654321, moderator.getUserId());
        assertEquals("moderator_name", moderator.getUsername());

        EventUser bannedUser = event.getBannedUser();
        assertNotNull(bannedUser);
        assertEquals(135790135, bannedUser.getUserId());
        assertEquals("banned_user_name", bannedUser.getUsername());

        BanMetadata metadata = event.getMetadata();
        assertNotNull(metadata);
        assertEquals("banned reason", metadata.getReason());
        assertEquals(Instant.parse("2025-01-14T16:08:05Z"), metadata.getCreatedAt());
        assertEquals(Instant.parse("2025-01-14T16:10:06Z"), metadata.getExpiresAt());
    }
}
