package pl.teksusik.kick4j.channels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies deserialization of a {@code GET /public/v1/channels} entry.
 */
public class ChannelTest {

    private static final String PAYLOAD = """
            {
              "active_gifted_subscribers_count": 30,
              "active_subscribers_count": 150,
              "banner_picture": "https://kick.com/img/default-banner-pictures/default2.jpeg",
              "broadcaster_user_id": 123,
              "canceled_subscribers_count": 25,
              "category": {
                "id": 101,
                "name": "Old School Runescape",
                "thumbnail": "https://kick.com/img/categories/old-school-runescape.jpeg"
              },
              "channel_description": "Channel description",
              "slug": "john-doe",
              "stream": {
                "custom_tags": ["tag1", "tag2"],
                "is_live": true,
                "is_mature": true,
                "key": "super-secret-stream-key",
                "language": "en",
                "start_time": "0001-01-01T00:00:00Z",
                "thumbnail": "https://kick.com/img/default-thumbnail-pictures/default2.jpeg",
                "url": "rtmps://stream.kick.com/1234567890",
                "viewer_count": 67
              },
              "stream_title": "My first stream"
            }
            """;

    @Test
    public void deserializes_channel_with_gifted_subscribers_count() throws Exception {
        Channel channel = new ObjectMapper().readValue(PAYLOAD, Channel.class);

        assertEquals(30, channel.getActiveGiftedSubscribersCount());
        assertEquals(150, channel.getActiveSubscribersCount());
        assertEquals(120, channel.getActiveSubscribersCount() - channel.getActiveGiftedSubscribersCount());
        assertEquals(25, channel.getCanceledSubscribersCount());
        assertEquals("https://kick.com/img/default-banner-pictures/default2.jpeg", channel.getBannerPicture());
        assertEquals(123, channel.getBroadcasterUserId());
        assertEquals(101, channel.getCategory().getId());
        assertEquals("Old School Runescape", channel.getCategory().getName());
        assertEquals("Channel description", channel.getChannelDescription());
        assertEquals("john-doe", channel.getSlug());
        assertEquals("My first stream", channel.getStreamTitle());
        assertTrue(channel.getStream().isLive());
        assertEquals(List.of("tag1", "tag2"), channel.getStream().getCustomTags());
        assertEquals(67, channel.getStream().getViewerCount());
    }
}
