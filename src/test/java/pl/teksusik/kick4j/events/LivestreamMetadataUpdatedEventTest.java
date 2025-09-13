package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.categories.Category;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.LivestreamMetadata;
import pl.teksusik.kick4j.events.type.LivestreamMetadataUpdatedEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LivestreamMetadataUpdatedEventTest extends KickEventDispatcherTest {
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
              "metadata": {
                "title": "Stream Title",
                "language": "en",
                "has_mature_content": true,
                "category": {
                  "id": 123,
                  "name": "Category name",
                  "thumbnail": "http://example.com/image123"
                }
              }
            }""";

    @Test
    public void should_deserialize_livestream_metadata_updated_event() {
        LivestreamMetadataUpdatedEvent event = dispatchAndCapture(
                LivestreamMetadataUpdatedEvent.class,
                "livestream.metadata.updated",
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

        LivestreamMetadata metadata = event.getMetadata();
        assertNotNull(metadata);
        assertEquals("Stream Title", metadata.getTitle());
        assertEquals("en", metadata.getLanguage());
        assertTrue(metadata.getHasMatureContent());

        Category category = metadata.getCategory();
        assertNotNull(category);
        assertEquals(123, category.getId());
        assertEquals("Category name", category.getName());
        assertEquals("http://example.com/image123", category.getThumbnail());
    }
}
