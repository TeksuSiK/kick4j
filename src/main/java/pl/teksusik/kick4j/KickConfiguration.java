package pl.teksusik.kick4j;

import lombok.Builder;
import lombok.Getter;
import pl.teksusik.kick4j.authorization.RefreshTokenStore;

@Getter
@Builder
public final class KickConfiguration {
    private RefreshTokenStore tokenStore;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    @Builder.Default
    private String oAuthHost = "https://id.kick.com";
    @Builder.Default
    private String authorizationEndpoint = "/oauth/authorize";
    @Builder.Default
    private String tokenEndpoint = "/oauth/token";
    @Builder.Default
    private String baseUrl = "https://api.kick.com/public/v1";
    @Builder.Default
    private String categories = "/categories";
    @Builder.Default
    private String categoriesId = "/categories/{id}";
    @Builder.Default
    private String tokenIntrospect = "/token/introspect";
    @Builder.Default
    private String users = "/users";
    @Builder.Default
    private String channels = "/channels";
    @Builder.Default
    private String chat = "/chat";
    @Builder.Default
    private String moderation = "/moderation/bans";
    @Builder.Default
    private String livestreams = "/livestreams";
    @Builder.Default
    private String publicKey = "/public-key";
    @Builder.Default
    private String events = "/events/subscriptions";
}
