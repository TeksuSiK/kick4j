package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChatMessageSentEvent;
import pl.teksusik.kick4j.events.type.Emote;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.Identity;
import pl.teksusik.kick4j.events.type.ReplyInfo;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatMessageSentEventTest extends KickEventDispatcherTest {
    private static final String PAYLOAD = """
            {
              "message_id": "unique_message_id_123",
              "replies_to": {
                "message_id": "unique_message_id_456",
                "content": "This is the parent message!",
                "sender": {
                  "is_anonymous": false,
                  "user_id": 12345,
                  "username": "parent_sender_name",
                  "is_verified": false,
                  "profile_picture": "https://example.com/parent_sender_avatar.jpg",
                  "channel_slug": "parent_sender_channel",
                  "identity": null
                }
              },
              "broadcaster": {
                "is_anonymous": false,
                "user_id": 123456789,
                "username": "broadcaster_name",
                "is_verified": true,
                "profile_picture": "https://example.com/broadcaster_avatar.jpg",
                "channel_slug": "broadcaster_channel",
                "identity": null
              },
              "sender": {
                "is_anonymous": false,
                "user_id": 987654321,
                "username": "sender_name",
                "is_verified": false,
                "profile_picture": "https://example.com/sender_avatar.jpg",
                "channel_slug": "sender_channel",
                "identity": {
                  "username_color": "#FF5733",
                  "badges": [
                    {
                      "text": "Moderator",
                      "type": "moderator"
                    },
                    {
                      "text": "Sub Gifter",
                      "type": "sub_gifter",
                      "count": 5
                    },
                    {
                      "text": "Subscriber",
                      "type": "subscriber",
                      "count": 3
                    }
                  ]
                }
              },
              "content": "This is a test message with emotes!",
              "emotes": [
                {
                  "emote_id": "12345",
                  "positions": [
                    { "s": 0, "e": 7 }
                  ]
                },
                {
                  "emote_id": "67890",
                  "positions": [
                    { "s": 20, "e": 25 }
                  ]
                }
              ],
              "created_at": "2025-01-14T16:08:06Z"
            }""";

    @Test
    public void should_deserialize_chat_message_sent_event() {
        ChatMessageSentEvent event = dispatchAndCapture(
                ChatMessageSentEvent.class,
                "chat.message.sent",
                "1",
                PAYLOAD
        );

        assertEquals("unique_message_id_123", event.getMessageId());

        ReplyInfo reply = event.getRepliesTo();
        assertNotNull(reply);
        assertEquals("unique_message_id_456", reply.getMessageId());
        assertEquals("This is the parent message!", reply.getContent());

        EventUser replySender = reply.getSender();
        assertNotNull(replySender);
        assertFalse(replySender.getAnonymous());
        assertEquals(12345, replySender.getUserId());
        assertEquals("parent_sender_name", replySender.getUsername());
        assertFalse(replySender.getVerified());
        assertEquals("https://example.com/parent_sender_avatar.jpg", replySender.getProfilePicture());
        assertEquals("parent_sender_channel", replySender.getChannelSlug());
        assertNull(replySender.getIdentity());

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertFalse(broadcaster.getAnonymous());
        assertEquals(123456789, broadcaster.getUserId());
        assertEquals("broadcaster_name", broadcaster.getUsername());
        assertTrue(broadcaster.getVerified());
        assertEquals("https://example.com/broadcaster_avatar.jpg", broadcaster.getProfilePicture());
        assertEquals("broadcaster_channel", broadcaster.getChannelSlug());
        assertNull(broadcaster.getIdentity());

        EventUser sender = event.getSender();
        assertNotNull(sender);
        assertFalse(sender.getAnonymous());
        assertEquals(987654321, sender.getUserId());
        assertEquals("sender_name", sender.getUsername());
        assertFalse(sender.getVerified());
        assertEquals("https://example.com/sender_avatar.jpg", sender.getProfilePicture());
        assertEquals("sender_channel", sender.getChannelSlug());
        assertNotNull(sender.getIdentity());

        Identity identity = sender.getIdentity();
        assertEquals("#FF5733", identity.getUsernameColor());
        assertEquals(3, identity.getBadges().size());

        assertEquals("Moderator", identity.getBadges().get(0).getText());
        assertEquals("moderator", identity.getBadges().get(0).getType());
        assertNull(identity.getBadges().get(0).getCount());

        assertEquals("Sub Gifter", identity.getBadges().get(1).getText());
        assertEquals("sub_gifter", identity.getBadges().get(1).getType());
        assertEquals(5, identity.getBadges().get(1).getCount());

        assertEquals("Subscriber", identity.getBadges().get(2).getText());
        assertEquals("subscriber", identity.getBadges().get(2).getType());
        assertEquals(3, identity.getBadges().get(2).getCount());

        assertEquals("This is a test message with emotes!", event.getContent());

        List<Emote> emotes = event.getEmotes();
        assertNotNull(emotes);
        assertEquals(2, emotes.size());

        Emote firstEmote = emotes.get(0);
        assertEquals("12345", firstEmote.getEmoteId());
        assertEquals("0", firstEmote.getPositions().get(0).getStart());
        assertEquals("7", firstEmote.getPositions().get(0).getEnd());

        Emote secondEmote = emotes.get(1);
        assertEquals("67890", secondEmote.getEmoteId());
        assertEquals("20", secondEmote.getPositions().get(0).getStart());
        assertEquals("25", secondEmote.getPositions().get(0).getEnd());

        assertEquals(Instant.parse("2025-01-14T16:08:06Z"), event.getCreatedAt());
    }
}
