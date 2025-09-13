package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChannelFollowedEvent;
import pl.teksusik.kick4j.events.type.EventUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChannelFollowedEventTest extends KickEventDispatcherTest {
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
              "follower": {
                "is_anonymous": false,
                "user_id": 987654321,
                "username": "follower_name",
                "is_verified": false,
                "profile_picture": "https://example.com/sender_avatar.jpg",
                "channel_slug": "follower_channel",
                "identity": null
              }
            }""";

    @Test
    public void should_deserialize_channel_followed_event() {
        ChannelFollowedEvent event = dispatchAndCapture(
                ChannelFollowedEvent.class,
                "channel.followed",
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

        EventUser follower = event.getFollower();
        assertNotNull(follower);
        assertFalse(follower.getAnonymous());
        assertEquals(987654321, follower.getUserId());
        assertEquals("follower_name", follower.getUsername());
        assertFalse(follower.getVerified());
        assertEquals("https://example.com/sender_avatar.jpg", follower.getProfilePicture());
        assertEquals("follower_channel", follower.getChannelSlug());
        assertNull(follower.getIdentity());
    }
}
