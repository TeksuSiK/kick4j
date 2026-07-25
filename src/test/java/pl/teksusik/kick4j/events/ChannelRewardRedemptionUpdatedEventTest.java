package pl.teksusik.kick4j.events;

import org.junit.jupiter.api.Test;
import pl.teksusik.kick4j.events.type.ChannelRewardRedemptionUpdatedEvent;
import pl.teksusik.kick4j.events.type.EventUser;
import pl.teksusik.kick4j.events.type.RedemptionReward;
import pl.teksusik.kick4j.rewards.RedemptionStatus;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChannelRewardRedemptionUpdatedEventTest extends KickEventDispatcherTest {
    private static final String PAYLOAD = """
            {
              "id": "01KBHE78QE4HZY1617DK5FC7YD",
              "user_input": "unban me",
              "status": "rejected",
              "redeemed_at": "2025-12-02T22:54:19.323Z",
              "reward": {
                "id": "01KBHE7RZNHB0SKDV1H86CD4F3",
                "title": "Uban Request",
                "cost": 1000,
                "description": "Only good reasons pls"
              },
              "redeemer": {
                "user_id": 123,
                "username": "naughty-user",
                "is_verified": false,
                "profile_picture": "",
                "channel_slug": "naughty_user"
              },
              "broadcaster": {
                "user_id": 333,
                "username": "gigachad",
                "is_verified": true,
                "profile_picture": "",
                "channel_slug": "gigachad"
              }
            }""";

    @Test
    public void should_deserialize_channel_reward_redemption_updated_event() {
        ChannelRewardRedemptionUpdatedEvent event = dispatchAndCapture(
                ChannelRewardRedemptionUpdatedEvent.class,
                "channel.reward.redemption.updated",
                "1",
                PAYLOAD
        );

        assertEquals("01KBHE78QE4HZY1617DK5FC7YD", event.getId());
        assertEquals("unban me", event.getUserInput());
        assertEquals(RedemptionStatus.REJECTED, event.getStatus());
        assertEquals(Instant.parse("2025-12-02T22:54:19.323Z"), event.getRedeemedAt());

        RedemptionReward reward = event.getReward();
        assertNotNull(reward);
        assertEquals("01KBHE7RZNHB0SKDV1H86CD4F3", reward.getId());
        assertEquals("Uban Request", reward.getTitle());
        assertEquals(1000, reward.getCost());
        assertEquals("Only good reasons pls", reward.getDescription());

        EventUser redeemer = event.getRedeemer();
        assertNotNull(redeemer);
        assertEquals(123, redeemer.getUserId());
        assertEquals("naughty-user", redeemer.getUsername());

        EventUser broadcaster = event.getBroadcaster();
        assertNotNull(broadcaster);
        assertEquals(333, broadcaster.getUserId());
        assertEquals("gigachad", broadcaster.getUsername());
    }
}
