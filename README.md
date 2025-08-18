# Kick4j

A comprehensive Java library for interacting with the Kick.com streaming platform API. Kick4j provides easy-to-use clients for all major Kick API endpoints, OAuth 2.0 authentication with PKCE support, and built-in webhook handling for real-time events.

## Features

- **Complete API Coverage**: Support for all official Kick.com API endpoints
    - User management and authentication
    - Channel operations and metadata
    - Chat messaging
    - Livestream management
    - Moderation tools
    - Categories and discovery
    - Event subscriptions
- **OAuth 2.0 with PKCE**: Secure authentication flow implementation
- **Webhook Support**: Built-in webhook receiver with signature verification, for demonstration purposes
- **Event System**: Type-safe event handling for real-time notifications
- **Flexible Configuration**: Customizable endpoints and settings
- **Token Management**: Automatic token refresh and storage

## Installation

### Gradle

Add the repository to your `build.gradle` or `build.gradle.kts`:

```gradle
repositories {
    mavenCentral()
    maven {
        name = "teksusik"
        url = "https://repo.teksusik.pl/releases"
    }
}

dependencies {
    implementation 'pl.teksusik:kick4j:1.0.0'
}
```

### Maven

Add the repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>teksusik-snapshots</id>
        <url>https://repo.teksusik.pl/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>pl.teksusik</groupId>
        <artifactId>kick4j</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Quick Start

### 1. Basic Setup

```java
import pl.teksusik.kick4j.KickClient;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.FileRefreshTokenStore;

// Configure the client
KickConfiguration config = KickConfiguration.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .redirectUri("http://localhost:8080/callback")
    .tokenStore(new FileRefreshTokenStore(Paths.get("refresh_token.txt")))
    .build();

// Create the client
KickClient client = new KickClient(config);
```

### 2. OAuth Authentication

```java
import pl.teksusik.kick4j.authorization.Scope;
import static pl.teksusik.kick4j.authorization.AuthorizationClient.*;

// Generate PKCE codes
String codeVerifier = generateCodeVerifier();
String codeChallenge = generateCodeChallenge(codeVerifier);

// Get authorization URL
String authUrl = client.authorization().getAuthorizationUrl(
    List.of(Scope.USER_READ, Scope.CHANNEL_READ, Scope.CHAT_WRITE),
    codeChallenge
);

System.out.println("Visit: " + authUrl);

// After user authorization, exchange code for tokens
String authCode = "code-from-callback";
OAuthTokenResponse tokens = client.authorization()
    .exchangeCodeForToken(authCode, codeVerifier);

client.authorization().setTokens(tokens);
```

### 3. Basic API Usage

```java
// Get current user
User currentUser = client.users().getCurrentUser();
System.out.println("Hello, " + currentUser.getName());

// Get current channel
Channel channel = client.channels().getCurrentChannel();
System.out.println("Channel: " + channel.getSlug());

// Send a chat message
PostChatMessageRequest chatRequest = PostChatMessageRequest.builder()
    .broadcastUserId(channel.getBroadcasterUserId())
    .content("Hello from Kick4j!")
    .type(PostChatMessageRequest.Type.BOT)
    .build();

client.chat().postChatMessage(chatRequest);

// Update channel information
UpdateChannelRequest updateRequest = UpdateChannelRequest.builder()
    .streamTitle("New Stream Title")
    .categoryId(12) 
    .build();

client.channels().updateChannel(updateRequest);
```

## Advanced Usage

### Event Webhooks

Kick4j provides built-in webhook support for handling real-time events:

```java
// Register event listeners
client.eventDispatcher().registerListener(
    ChatMessageSentEvent.class,
    event -> {
        System.out.println(event.getSender().getUsername() + 
                          " said: " + event.getContent());
    }
);

client.eventDispatcher().registerListener(
    ChannelFollowedEvent.class,
    event -> {
        System.out.println("New follower: " + 
                          event.getFollower().getUsername());
    }
);

// Start webhook receiver
client.startWebhookReceiver("/webhooks", 8080);

// Subscribe to events
EventSubscriptionRequest subscription = EventSubscriptionRequest.builder()
    .broadcasterUserId(currentUser.getUserId())
    .addEvent(new EventSubscriptionRequest.Event("chat.message.sent", 1))
    .addEvent(new EventSubscriptionRequest.Event("channel.followed", 1))
    .method(EventSubscriptionRequest.Method.WEBHOOK)
    .build();

client.events().postEventsSubscription(subscription);
```

### Supported Events

- `chat.message.sent` - New chat messages
- `channel.followed` - New followers
- `channel.subscription.new` - New subscriptions
- `channel.subscription.renewal` - Subscription renewals
- `channel.subscription.gifts` - Gift subscriptions
- `livestream.status.updated` - Stream start/stop
- `livestream.metadata.updated` - Stream title/category changes
- `moderation.banned` - User bans/timeouts

### Moderation

```java
// Ban a user
PostModerationBansRequest banRequest = PostModerationBansRequest.builder()
    .broadcasterUserId(channel.getBroadcasterUserId())
    .userId(123456)
    .duration(3600)
    .reason("Spam")
    .build();

client.moderation().postModerationBans(banRequest);

// Unban a user
client.moderation().deleteModerationBans(
    channel.getBroadcasterUserId(), 
    123456
);
```

### Livestream Management

```java
// Get current livestream
Livestream stream = client.livestreams()
    .getLivestream(channel.getBroadcasterUserId());

if (stream != null) {
    System.out.println("Currently streaming: " + stream.getStreamTitle());
    System.out.println("Viewers: " + stream.getViewerCount());
}

// Search livestreams
GetLivestreamsRequest searchRequest = GetLivestreamsRequest.builder()
    .category(12) // Gaming
    .language("en")
    .limit(10)
    .sort(GetLivestreamsRequest.Sort.VIEWER_COUNT)
    .build();

List<Livestream> streams = client.livestreams().getLivestreams(searchRequest);
```

### Categories

```java
// Search categories
List<Category> categories = client.categories().getCategories("gaming");

// Get specific category
Category category = client.categories().getCategory(12);
System.out.println("Category: " + category.getName());
```

## Configuration

### Custom Token Storage

Implement the `RefreshTokenStore` interface for custom token storage:

```java
public class DatabaseTokenStore implements RefreshTokenStore {
    @Override
    public String getRefreshToken() {
        // Retrieve from database
        return database.getRefreshToken();
    }

    @Override
    public void notifyRefreshTokenRoll(String newRefreshToken) {
        // Store in database
        database.saveRefreshToken(newRefreshToken);
    }
}
```

### Custom Configuration

```java
KickConfiguration config = KickConfiguration.builder()
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .redirectUri("https://your-app.com/callback")
    .tokenStore(new DatabaseTokenStore())
    .baseUrl("https://api.kick.com/public/v1") // Custom API base URL
    .oAuthHost("https://id.kick.com") // Custom OAuth host
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

- Java 11 or higher
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