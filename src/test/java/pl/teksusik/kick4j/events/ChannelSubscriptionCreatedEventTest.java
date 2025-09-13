package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChannelSubscriptionCreatedEvent;
import pl.teksusik.kick4j.events.type.EventUser;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChannelSubscriptionCreatedEventTest extends KickEventDispatcherTest {
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
              "subscriber": {
                "is_anonymous": false,
                "user_id": 987654321,
                "username": "subscriber_name",
                "is_verified": false,
                "profile_picture": "https://example.com/sender_avatar.jpg",
                "channel_slug": "subscriber_channel",
                "identity": null
              },
              "duration": 1,
              "created_at": "2025-01-14T16:08:06Z",
              "expires_at": "2025-02-14T16:08:06Z"
            }""";

    @Test
    public void should_deserialize_channel_subscription_created_event() {
        ChannelSubscriptionCreatedEvent event = dispatchAndCapture(
                ChannelSubscriptionCreatedEvent.class,
                "channel.subscription.new",
                "1",
                PAYLOAD
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

        EventUser subscriber = event.getSubscriber();
        assertNotNull(subscriber);
        assertFalse(subscriber.getAnonymous());
        assertEquals(987654321, subscriber.getUserId());
        assertEquals("subscriber_name", subscriber.getUsername());
        assertFalse(subscriber.getVerified());
        assertEquals("https://example.com/sender_avatar.jpg", subscriber.getProfilePicture());
        assertEquals("subscriber_channel", subscriber.getChannelSlug());
        assertNull(subscriber.getIdentity());

        assertEquals(1, event.getDuration());
        assertEquals(Instant.parse("2025-01-14T16:08:06Z"), event.getCreatedAt());
        assertEquals(Instant.parse("2025-02-14T16:08:06Z"), event.getExpiresAt());
    }
}
