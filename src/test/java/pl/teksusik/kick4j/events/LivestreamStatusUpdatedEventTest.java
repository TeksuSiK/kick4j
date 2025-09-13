package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.LivestreamStatusUpdatedEvent;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LivestreamStatusUpdatedEventTest extends KickEventDispatcherTest {
    private static final String STREAM_STARTED_PAYLOAD = """
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
              "is_live": true,
              "title": "Stream Title",
              "started_at": "2025-01-01T11:00:00+11:00",
              "ended_at": null
            }""";

    private static final String STREAM_ENDED_PAYLOAD = """
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
              "is_live": false,
              "title": "Stream Title",
              "started_at": "2025-01-01T11:00:00+11:00",
              "ended_at": "2025-01-01T15:00:00+11:00"
            }""";

    @Test
    public void should_deserialize_livestream_started_event() {
        LivestreamStatusUpdatedEvent event = dispatchAndCapture(
                LivestreamStatusUpdatedEvent.class,
                "livestream.status.updated",
                "1",
                STREAM_STARTED_PAYLOAD
        );

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertFalse(broadcaster.getAnonymous());
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());
        assertTrue(broadcaster.getVerified());
        assertEquals("https://example.com/broadcaster_avatar.jpg", broadcaster.getProfilePicture());
        assertEquals("broadcaster_channel", broadcaster.getChannelSlug());
        assertNull(broadcaster.getIdentity());

        assertTrue(event.getLive());
        assertEquals("Stream Title", event.getTitle());
        assertEquals(Instant.parse("2025-01-01T11:00:00+11:00"), event.getStartedAt());
        assertNull(event.getEndedAt());
    }

    @Test
    public void should_deserialize_livestream_ended_event() {
        LivestreamStatusUpdatedEvent event = dispatchAndCapture(
                LivestreamStatusUpdatedEvent.class,
                "livestream.status.updated",
                "1",
                STREAM_ENDED_PAYLOAD
        );

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertFalse(broadcaster.getAnonymous());
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());
        assertTrue(broadcaster.getVerified());
        assertEquals("https://example.com/broadcaster_avatar.jpg", broadcaster.getProfilePicture());
        assertEquals("broadcaster_channel", broadcaster.getChannelSlug());
        assertNull(broadcaster.getIdentity());

        assertFalse(event.getLive());
        assertEquals("Stream Title", event.getTitle());
        assertEquals(Instant.parse("2025-01-01T11:00:00+11:00"), event.getStartedAt());
        assertEquals(Instant.parse("2025-01-01T15:00:00+11:00"), event.getEndedAt());
    }
}
