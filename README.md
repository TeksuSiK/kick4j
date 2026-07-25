# Kick4j

A comprehensive Java library for interacting with the Kick.com streaming platform API. Kick4j provides easy-to-use clients for all major Kick API endpoints, OAuth 2.1 authentication (Authorization Code + PKCE **and** Client Credentials), and built-in webhook handling for real-time events.

## Features

- **Complete API Coverage**: Support for all official Kick.com API endpoints
    - User management and token introspection
    - Channel operations and metadata
    - Chat messaging and message deletion
    - Livestream discovery (V1 & paginated V2, plus livestreams by user)
    - Moderation (bans & timeouts)
    - Categories and discovery (V1 & paginated V2 with tags)
    - Channel Rewards (channel points) and redemptions
    - KICKs gifting leaderboard
    - Drops reward claims (Public API)
    - Event subscriptions
- **OAuth 2.1**: Both authentication flows
    - **User Access Token** — Authorization Code + PKCE (act on behalf of a user)
    - **App Access Token** — Client Credentials (server-to-server, no user login)
    - Token introspection and revocation
- **Explicit auth mode**: Choose `AuthMode.USER` or `AuthMode.APP` per client, override per request
- **Webhook Support**: Built-in webhook receiver with signature verification, for demonstration purposes
- **Event System**: Type-safe event handling for real-time notifications
- **Cursor Pagination**: First-class `PaginatedResponse<T>` for V2 endpoints
- **Flexible Configuration**: Customizable endpoints, base URLs and settings
- **Token Management**: Automatic user-token refresh and app-token renewal

## Installation

Snapshots are published to `https://repo.teksusik.pl/snapshots` and stable releases to `https://repo.teksusik.pl/releases`.

### Gradle

```gradle
repositories {
    mavenCentral()
    maven {
        name = "teksusik"
        url = "https://repo.teksusik.pl/snapshots"
    }
}

dependencies {
    implementation 'pl.teksusik:kick4j:1.2.0-SNAPSHOT'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>teksusik</id>
        <url>https://repo.teksusik.pl/snapshots</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>pl.teksusik</groupId>
        <artifactId>kick4j</artifactId>
        <version>1.2.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Authentication

Kick has two token types, each with its own OAuth flow. You must pick a default token type for the client via `AuthMode`:

- **`AuthMode.USER`** — user access token (Authorization Code + PKCE). Required for actions on behalf of a user: chat, moderation, channel updates, per-user event subscriptions.
- **`AuthMode.APP`** — app access token (Client Credentials). Server-to-server access to publicly available data (livestreams, categories, channels, users) and Drops. No user login required.

Individual requests can override the configured mode, and the `DropsClient` always uses an app token because the Drops API requires one.

### User flow (Authorization Code + PKCE)

```java
import pl.teksusik.kick4j.KickClient;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.AuthMode;
import pl.teksusik.kick4j.authorization.FileRefreshTokenStore;
import pl.teksusik.kick4j.authorization.OAuthTokenResponse;
import pl.teksusik.kick4j.authorization.Scope;

import java.nio.file.Paths;
import java.util.List;

import static pl.teksusik.kick4j.authorization.AuthorizationClient.generateCodeChallenge;
import static pl.teksusik.kick4j.authorization.AuthorizationClient.generateCodeVerifier;

KickConfiguration config = KickConfiguration.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .redirectUri("http://localhost:8080/callback")
    .tokenStore(new FileRefreshTokenStore(Paths.get("refresh_token.txt")))
    .authMode(AuthMode.USER)
    .build();

KickClient client = new KickClient(config);

// 1. Generate PKCE codes (keep the verifier until the callback)
String codeVerifier = generateCodeVerifier();
String codeChallenge = generateCodeChallenge(codeVerifier);

// 2. Send the user to the authorization URL
String authUrl = client.authorization().getAuthorizationUrl(
    List.of(Scope.USER_READ, Scope.CHANNEL_READ, Scope.CHAT_WRITE),
    codeChallenge
);
System.out.println("Visit: " + authUrl);

// 3. Exchange the callback code for tokens
OAuthTokenResponse tokens = client.authorization()
    .exchangeCodeForToken("code-from-callback", codeVerifier);
client.authorization().setTokens(tokens);
```

The access token is refreshed automatically when it is about to expire (the refresh token is persisted and rotated via your `RefreshTokenStore`). You are responsible for the redirect/callback handling and for validating the `state` parameter.

### App flow (Client Credentials)

An app-only client needs only `clientId` + `clientSecret` (no `redirectUri` or `tokenStore`):

```java
import pl.teksusik.kick4j.authorization.AuthMode;

KickConfiguration config = KickConfiguration.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .authMode(AuthMode.APP)
    .build();

KickClient client = new KickClient(config);

// Requests now use an app token automatically (fetched and renewed on demand):
client.livestreams().getLivestreamsV2();
client.categories().getCategoriesV2();

// You can also manage the app token yourself:
String appToken = client.authorization().getAppAccessToken();
```

### Token introspection & revocation

```java
import pl.teksusik.kick4j.authorization.TokenTypeHint;
import pl.teksusik.kick4j.users.TokenIntrospect;

// Introspect the current (or a specific) token — POST /oauth/token/introspect
TokenIntrospect info = client.authorization().introspectToken();
System.out.println("active=" + info.isActive() + ", scope=" + info.getScope());

// Revoke a token — POST /oauth/revoke
client.authorization().revokeToken(someToken, TokenTypeHint.REFRESH_TOKEN);
```

### Scopes

`user:read`, `channel:read`, `channel:write`, `channel:rewards:read`, `channel:rewards:write`, `chat:write`, `streamkey:read`, `events:subscribe`, `moderation:ban`, `moderation:chat_message:manage`, `kicks:read` — available via the `Scope` enum.

## Basic API Usage

```java
// Get current user (defaults to the authorised user)
User currentUser = client.users().getCurrentUser();
System.out.println("Hello, " + currentUser.getName());

// Get current channel
Channel channel = client.channels().getCurrentChannel();
System.out.println("Channel: " + channel.getSlug()
    + " | subscribers: " + channel.getActiveSubscribersCount());

// Send a chat message
PostChatMessageRequest chatRequest = PostChatMessageRequest.builder()
    .broadcastUserId(channel.getBroadcasterUserId())
    .content("Hello from Kick4j!")
    .type(PostChatMessageRequest.Type.BOT)
    .build();
PostChatMessageResponse sent = client.chat().postChatMessage(chatRequest);

// Delete a chat message (scope moderation:chat_message:manage)
client.chat().deleteChatMessage(sent.getMessageId());

// Update channel information
UpdateChannelRequest updateRequest = UpdateChannelRequest.builder()
    .streamTitle("New Stream Title")
    .categoryId(12)
    .customTags(List.of("speedrun", "chill"))
    .build();
client.channels().updateChannel(updateRequest);
```

## Categories

The V1 endpoints are deprecated by Kick; prefer the paginated V2 endpoint which also returns tags.

```java
import pl.teksusik.kick4j.api.PaginatedResponse;
import pl.teksusik.kick4j.categories.CategoryWithTags;
import pl.teksusik.kick4j.categories.GetCategoriesV2Request;

PaginatedResponse<CategoryWithTags> page = client.categories().getCategoriesV2(
    GetCategoriesV2Request.builder()
        .names(List.of("Just Chatting"))
        .tags(List.of("IRL"))
        .limit(50)
        .build());

page.getData().forEach(c ->
    System.out.println(c.getName() + " " + c.getTags()));

// Fetch the next page with the returned cursor
String nextCursor = page.getPagination().getNextCursor();

// Deprecated V1 (still works for now):
// List<Category> results = client.categories().getCategories("gaming");
```

## Livestreams

```java
import pl.teksusik.kick4j.api.PaginatedResponse;
import pl.teksusik.kick4j.livestreams.GetLivestreamsV2Request;
import pl.teksusik.kick4j.livestreams.LivestreamV2;

// Paginated V2 discovery (oldest to newest)
PaginatedResponse<LivestreamV2> streams = client.livestreams().getLivestreamsV2(
    GetLivestreamsV2Request.builder()
        .categoryId(List.of(12))
        .languageCode(List.of("en"))
        .limit(100)
        .build());

streams.getData().forEach(s ->
    System.out.println(s.getTitle() + " – " + s.getViewerCount()));

// Active livestreams for specific broadcasters (max 100 user IDs)
List<LivestreamV2> byUsers = client.livestreams().getUserLivestreams(123, 456);

// Global livestream stats
LivestreamsStats stats = client.livestreams().getLivestreamsStats();

// Deprecated V1 (still works for now):
// Livestream stream = client.livestreams().getLivestream(broadcasterUserId);
```

## Moderation

```java
// Ban (omit duration) or timeout (duration in minutes, 1–10080) a user
PostModerationBansRequest banRequest = PostModerationBansRequest.builder()
    .broadcasterUserId(channel.getBroadcasterUserId())
    .userId(123456)
    .duration(60)
    .reason("Spam")
    .build();
client.moderation().postModerationBans(banRequest);

// Unban / remove a timeout
client.moderation().deleteModerationBans(channel.getBroadcasterUserId(), 123456);
```

## Channel Rewards (channel points)

Requires the `channel:rewards:read` / `channel:rewards:write` scopes.

```java
import pl.teksusik.kick4j.rewards.ChannelReward;
import pl.teksusik.kick4j.rewards.CreateChannelRewardRequest;
import pl.teksusik.kick4j.rewards.GetRedemptionsRequest;
import pl.teksusik.kick4j.rewards.RedemptionStatus;
import pl.teksusik.kick4j.rewards.RedemptionsByReward;

// List rewards (up to 15 per channel)
List<ChannelReward> rewards = client.channelRewards().getRewards();

// Create a reward (cost and title are required)
ChannelReward reward = client.channelRewards().createReward(
    CreateChannelRewardRequest.builder()
        .title("Song Request")
        .cost(100)
        .description("Request a song by providing a URL")
        .isUserInputRequired(true)
        .build());

// List pending redemptions (grouped by reward), then accept/reject them
List<RedemptionsByReward> pending = client.channelRewards().getRedemptions(
    GetRedemptionsRequest.builder().status(RedemptionStatus.PENDING).build());

client.channelRewards().acceptRedemptions("redemption-id-1", "redemption-id-2");
client.channelRewards().rejectRedemptions("redemption-id-3");

// Update or delete a reward
client.channelRewards().deleteReward(reward.getId());
```

## KICKs leaderboard

Requires the `kicks:read` scope.

```java
import pl.teksusik.kick4j.kicks.KicksLeaderboard;

KicksLeaderboard leaderboard = client.kicks().getLeaderboard(10); // top 10
leaderboard.getWeek().forEach(entry ->
    System.out.println("#" + entry.getRank() + " " + entry.getUsername()
        + " – " + entry.getGiftedAmount()));
```

## Drops (Public API)

The Drops endpoints require an **app** access token (the `DropsClient` uses one automatically). Only OAuth apps associated with an organization can access them.

```java
import pl.teksusik.kick4j.drops.DropClaimUpdate;
import pl.teksusik.kick4j.drops.DropClaimsResponse;
import pl.teksusik.kick4j.drops.GetDropClaimsRequest;

// Retrieve claims (cursor is nested in the response — pass it back to page)
DropClaimsResponse claims = client.drops().getClaims(
    GetDropClaimsRequest.builder()
        .campaignId("01JAXK8N4QWRTY5PM7ZEBVJDS8S")
        .limit(50)
        .build());
String next = claims.getCursor();

// Update the external_status of up to 100 claims
client.drops().updateClaims(List.of(
    new DropClaimUpdate("01KAAFHJ2PNXS48NG8XXPGWKCZ", "processed")));
```

## Events & Webhooks

Kick4j provides built-in webhook support for handling real-time events:

```java
// Register event listeners
client.eventDispatcher().registerListener(
    ChatMessageSentEvent.class,
    event -> System.out.println(event.getSender().getUsername()
        + " said: " + event.getContent()));

client.eventDispatcher().registerListener(
    KicksGiftedEvent.class,
    event -> System.out.println(event.getSender().getUsername()
        + " gifted " + event.getGift().getAmount() + " kicks"));

// Start webhook receiver (for demonstration purposes)
client.startWebhookReceiver("/webhooks", 8080);

// Subscribe to events
EventSubscriptionRequest subscription = EventSubscriptionRequest.builder()
    .broadcasterUserId(currentUser.getUserId())
    .addEvent(new EventSubscriptionRequest.Event("chat.message.sent", 1))
    .addEvent(new EventSubscriptionRequest.Event("kicks.gifted", 1))
    .method(EventSubscriptionRequest.Method.WEBHOOK)
    .build();
client.events().postEventsSubscription(subscription);
```

### Supported Events

| Event | Class | Version |
|-------|-------|---------|
| `chat.message.sent` | `ChatMessageSentEvent` | 1 |
| `channel.followed` | `ChannelFollowedEvent` | 1 |
| `channel.subscription.new` | `ChannelSubscriptionCreatedEvent` | 1 |
| `channel.subscription.renewal` | `ChannelSubscriptionRenewalEvent` | 1 |
| `channel.subscription.gifts` | `ChannelSubscriptionGiftsEvent` | 1 |
| `channel.reward.redemption.updated` | `ChannelRewardRedemptionUpdatedEvent` | 1 |
| `livestream.status.updated` | `LivestreamStatusUpdatedEvent` | 1 |
| `livestream.metadata.updated` | `LivestreamMetadataUpdatedEvent` | 1 |
| `moderation.banned` | `ModerationBannedEvent` | 1 |
| `kicks.gifted` | `KicksGiftedEvent` | 1 |

## Configuration

### Custom Token Storage

Implement the `RefreshTokenStore` interface for custom token storage (only needed for the user flow):

```java
public class DatabaseTokenStore implements RefreshTokenStore {
    @Override
    public String getRefreshToken() {
        return database.getRefreshToken();
    }

    @Override
    public void notifyRefreshTokenRoll(String newRefreshToken) {
        database.saveRefreshToken(newRefreshToken);
    }
}
```

### Custom Configuration

All endpoints, base URLs and the OAuth host are configurable:

```java
KickConfiguration config = KickConfiguration.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .authMode(AuthMode.USER)
    .redirectUri("https://your-app.com/callback")
    .tokenStore(new DatabaseTokenStore())
    .baseUrl("https://api.kick.com/public/v1")   // V1 API base URL
    .baseUrlV2("https://api.kick.com/public/v2") // V2 API base URL
    .oAuthHost("https://id.kick.com")            // OAuth host
    .build();
```

## Error Handling

Kick4j throws specific exceptions for different error conditions:

```java
try {
    User user = client.users().getCurrentUser();
} catch (ApiException e) {
    System.err.println("API Error " + e.getErrorCode() + ": " + e.getPayload());
} catch (OAuthTokenException e) {
    System.err.println("Auth Error " + e.getErrorCode() + ": " + e.getPayload());
    // Handle token refresh or re-authentication
}
```

## Requirements

- Java 21 or higher
- Valid Kick.com application credentials

## Dependencies

- Jackson (JSON processing)
- Java HTTP Client (built-in)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

For questions, issues, or feature requests, please open an issue on the GitHub repository.

## Disclaimer

This library is not officially affiliated with Kick.com. Use at your own risk and ensure compliance with Kick.com's Terms of Service and API usage guidelines.
