package pl.teksusik.kick4j;

import lombok.Builder;
import lombok.Getter;
import pl.teksusik.kick4j.authorization.RefreshTokenStore;

@Getter
@Builder
public final class KickConfiguration {
    private final RefreshTokenStore tokenStore;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    @Builder.Default
    private final String oAuthHost = "https://id.kick.com";
    @Builder.Default
    private final String authorizationEndpoint = "/oauth/authorize";
    @Builder.Default
    private final String tokenEndpoint = "/oauth/token";
    @Builder.Default
    private final String baseUrl = "https://api.kick.com/public/v1";
    @Builder.Default
    private final String categories = "/categories";
    @Builder.Default
    private final String categoriesId = "/categories/{id}";
    @Builder.Default
    private final String tokenIntrospect = "/token/introspect";
    @Builder.Default
    private final String users = "/users";
    @Builder.Default
    private final String channels = "/channels";
    @Builder.Default
    private final String chat = "/chat";
    @Builder.Default
    private final String moderation = "/moderation/bans";
    @Builder.Default
    private final String livestreams = "/livestreams";
    @Builder.Default
    private final String publicKey = "/public-key";
    @Builder.Default
    private final String events = "/events/subscriptions";
}
