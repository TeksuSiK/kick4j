package pl.teksusik.kick4j.events;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChannelSubscriptionCreatedEvent;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.Gift;
import pl.teksusik.kick4j.events.type.KicksGiftedEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                 "amount": 100,
                 "name": "Full Send",
                 "type": "BASIC",
                 "tier": "BASIC",
                 "message": "w"
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

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());
        assertTrue(broadcaster.getVerified());
        assertEquals("https://example.com/broadcaster_avatar.jpg", broadcaster.getProfilePicture());
        assertEquals("broadcaster_channel", broadcaster.getChannelSlug());
        assertNull(broadcaster.getIdentity());

        EventUser sender = event.getSender();
        assertNotNull(sender);
        assertEquals(987654321, sender.getUserId());
        assertEquals("gift_sender", sender.getUsername());
        assertFalse(sender.getVerified());
        assertEquals("https://example.com/sender_avatar.jpg", sender.getProfilePicture());
        assertEquals("gift_sender_channel", sender.getChannelSlug());
        assertNull(sender.getIdentity());

        Gift gift = event.getGift();
        assertNotNull(gift);
        assertEquals(100, gift.getAmount());
        assertEquals("Full Send", gift.getName());
        assertEquals("BASIC", gift.getType());
        assertEquals("BASIC", gift.getTier());
        assertEquals("w", gift.getMessage());

        assertEquals(Instant.parse("2025-10-20T04:00:08.634Z"), event.getCreatedAt());
    }
}
