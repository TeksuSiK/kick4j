package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChannelSubscriptionGiftsEvent;
import pl.teksusik.kick4j.events.type.EventUser;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChannelSubscriptionGiftsEventTest extends KickEventDispatcherTest {
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
              "gifter": {
                "is_anonymous": false,
                "user_id": 987654321,
                "username": "gifter_name",
                "is_verified": false,
                "profile_picture": "https://example.com/sender_avatar.jpg",
                "channel_slug": "gifter_channel",
                "identity": null
              },
              "giftees":
              [
                {
                  "is_anonymous": false,
                  "user_id": 561654654,
                  "username": "giftee_name",
                  "is_verified": true,
                  "profile_picture": "https://example.com/broadcaster_avatar.jpg",
                  "channel_slug": "giftee_channel",
                  "identity": null
                }
              ],
              "created_at": "2025-01-14T16:08:06Z",
              "expires_at": "2025-02-14T16:08:06Z"
            }""";

    @Test
    public void should_deserialize_channel_subscription_gifts_event() {
        ChannelSubscriptionGiftsEvent event = dispatchAndCapture(
                ChannelSubscriptionGiftsEvent.class,
                "channel.subscription.gifts",
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

        EventUser gifter = event.getGifter();
        assertNotNull(gifter);
        assertFalse(gifter.getAnonymous());
        assertEquals(987654321, gifter.getUserId());
        assertEquals("gifter_name", gifter.getUsername());
        assertFalse(gifter.getVerified());
        assertEquals("https://example.com/sender_avatar.jpg", gifter.getProfilePicture());
        assertEquals("gifter_channel", gifter.getChannelSlug());
        assertNull(gifter.getIdentity());

        List<EventUser> giftees = event.getGiftees();
        assertNotNull(giftees);
        assertEquals(1, giftees.size());

        EventUser firstGiftee = giftees.get(0);
        assertFalse(firstGiftee.getAnonymous());
        assertEquals(561654654, firstGiftee.getUserId());
        assertEquals("giftee_name", firstGiftee.getUsername());
        assertTrue(firstGiftee.getVerified());
        assertEquals("https://example.com/broadcaster_avatar.jpg", firstGiftee.getProfilePicture());
        assertEquals("giftee_channel", firstGiftee.getChannelSlug());
        assertNull(firstGiftee.getIdentity());

        assertEquals(Instant.parse("2025-01-14T16:08:06Z"), event.getCreatedAt());
        assertEquals(Instant.parse("2025-02-14T16:08:06Z"), event.getExpiresAt());
    }
}
