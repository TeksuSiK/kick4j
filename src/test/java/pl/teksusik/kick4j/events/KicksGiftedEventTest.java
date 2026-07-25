package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.KicksGift;
import pl.teksusik.kick4j.events.type.KicksGiftedEvent;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KicksGiftedEventTest extends KickEventDispatcherTest {
    private static final String PAYLOAD = """
            {
              "broadcaster": {
                "user_id": 123456789,
                "username": "broadcaster_name",
                "is_verified": true,
                "profile_picture": "https://example.com/broadcaster_avatar.jpg",
                "channel_slug": "broadcaster_channel"
              },
              "sender": {
                "user_id": 987654321,
                "username": "gift_sender",
                "is_verified": false,
                "profile_picture": "https://example.com/sender_avatar.jpg",
                "channel_slug": "gift_sender_channel"
              },
              "gift": {
                "amount": 500,
                "name": "Rage Quit",
                "type": "LEVEL_UP",
                "tier": "MID",
                "message": "w",
                "pinned_time_seconds": 600
              },
              "created_at": "2025-10-20T04:00:08.634Z"
            }""";

    @Test
    public void should_deserialize_kicks_gifted_event() {
        KicksGiftedEvent event = dispatchAndCapture(
                KicksGiftedEvent.class,
                "kicks.gifted",
                "1",
                PAYLOAD
        );

        assertEquals(Instant.parse("2025-10-20T04:00:08.634Z"), event.getCreatedAt());

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());

        EventUser sender = event.getSender();
        assertNotNull(sender);
        assertEquals(987654321, sender.getUserId());
        assertEquals("gift_sender", sender.getUsername());

        KicksGift gift = event.getGift();
        assertNotNull(gift);
        assertEquals(500, gift.getAmount());
        assertEquals("Rage Quit", gift.getName());
        assertEquals("LEVEL_UP", gift.getType());
        assertEquals("MID", gift.getTier());
        assertEquals("w", gift.getMessage());
        assertEquals(600, gift.getPinnedTimeSeconds());
    }
}
