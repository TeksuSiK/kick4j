package pl.teksusik.kick4j.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * OAuth 2.0 Authorization client supporting PKCE flow for the Kick API.
 * <p>
 * Handles generation of authorization URLs, exchanging authorization codes for tokens,
 * refreshing access tokens, and managing token state.
 */
public class AuthorizationClient {
    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final KickConfiguration configuration;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;

    /**
     * Creates a new AuthorizationClient.
     *
     * @param httpClient   the HttpClient used for HTTP requests
     * @param mapper       ObjectMapper used to parse JSON responses
     * @param clientId     OAuth client ID issued by Kick
     * @param clientSecret OAuth client secret issued by Kick
     * @param redirectUri  Redirect URI registered with the OAuth provider
     */
    public AuthorizationClient(HttpClient httpClient, ObjectMapper mapper, String clientId, String clientSecret, String redirectUri) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.configuration = KickConfiguration.getConfiguration();

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     * Generates a cryptographically random code verifier for PKCE.
     * <p>
     * The verifier is a URL-safe base64 encoded string without padding, 32 bytes in length.
     *
     * @return a new code verifier string
     */
    public static String generateCodeVerifier() {
        byte[] code = new byte[32];
        new SecureRandom().nextBytes(code);
        return base64UrlEncoder(code);
    }

    /**
     * Generates a code challenge from the given code verifier using SHA-256 and Base64URL encoding,
     * as specified in the PKCE RFC.
     *
     * @param codeVerifier the code verifier string
     * @return the corresponding code challenge string
     */
    public static String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return base64UrlEncoder(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("Failed to generate code challenge", exception);
        }
    }

    /**
     * Builds the OAuth authorization URL where the user should be redirected to grant access.
     *
     * @param scopeList     list of OAuth scopes requested by the application
     * @param codeChallenge the PKCE code challenge
     * @return the full URL string for the authorization request
     */
    public String getAuthorizationUrl(List<Scope> scopeList, String codeChallenge) {
        StringJoiner joiner = new StringJoiner("&",
                this.configuration.getOAuthHost() + this.configuration.getAuthorizationEndpoint() + "?",
                "");
        joiner.add("client_id=" + encodeUrl(this.clientId));
        joiner.add("response_type=code");
        joiner.add("redirect_uri=" + encodeUrl(this.redirectUri));
        // Kick API requires state parameter but does not use it afterwards – generated here for best practice
        joiner.add("state=" + encodeUrl(UUID.randomUUID().toString()));
        String scopes = scopeList.stream()
                .map(Scope::getScope)
                .collect(Collectors.joining(" "));
        joiner.add("scope=" + encodeUrl(scopes));
        joiner.add("code_challenge=" + encodeUrl(codeChallenge));
        joiner.add("code_challenge_method=S256");

        return joiner.toString();
    }

    /**
     * Exchanges the authorization code received from the OAuth callback and the original
     * code verifier for access and refresh tokens.
     *
     * @param code         the authorization code returned by the OAuth server
     * @param codeVerifier the original PKCE code verifier
     * @return an OAuthTokenResponse containing access and refresh tokens
     */
    public OAuthTokenResponse exchangeCodeForToken(String code, String codeVerifier) {
        Map<String, String> body = Map.of(
                "code", code,
                "client_id", this.clientId,
                "client_secret", this.clientSecret,
                "redirect_uri", this.redirectUri,
                "grant_type", "authorization_code",
                "code_verifier", codeVerifier
        );

        return this.postOAuthTokenRequest(body);
    }

    /**
     * Refreshes the access token using the stored refresh token.
     *
     * @return a new OAuthTokenResponse containing refreshed access and refresh tokens
     */
    public OAuthTokenResponse refreshAccessToken() {
        Map<String, String> body = Map.of(
                "refresh_token", this.refreshToken,
                "client_id", this.clientId,
                "client_secret", this.clientSecret,
                "grant_type", "refresh_token"
        );

        return this.postOAuthTokenRequest(body);
    }

    /**
     * Returns a valid access token. Automatically refreshes the token if it is missing or expired.
     *
     * @return a valid OAuth access token string
     */
    public String getAccessToken() {
        if (this.accessToken == null || Instant.now().isAfter(this.expiresAt)) {
            OAuthTokenResponse response = this.refreshAccessToken();
            this.setTokens(response);
        }

        return this.accessToken;
    }

    /**
     * Updates the stored tokens and expiry time.
     *
     * @param oAuthToken the OAuthTokenResponse containing the new tokens and expiry
     */
    public void setTokens(OAuthTokenResponse oAuthToken) {
        this.accessToken = oAuthToken.getAccessToken();
        this.refreshToken = oAuthToken.getRefreshToken();
        this.expiresAt = Instant.now().plusSeconds(oAuthToken.getExpiresIn());
    }

    /**
     * Sets the access token manually.
     *
     * @param accessToken new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Sets the refresh token manually.
     *
     * @param refreshToken new refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Sets the expiration time of the current access token.
     *
     * @param expiresAt expiration timestamp
     */
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    private OAuthTokenResponse postOAuthTokenRequest(Map<String, String> body) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.configuration.getOAuthHost() + this.configuration.getTokenEndpoint()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(generateUrlencodedForm(body)))
                    .build();

            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new OAuthTokenException(response.statusCode(), response.body());
            }

            return this.mapper.readValue(response.body(), OAuthTokenResponse.class);
        } catch (IOException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to create OAuth token", exception);
        }
    }

    private static String generateUrlencodedForm(Map<String, String> data) {
        StringJoiner formData = new StringJoiner("&");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            formData.add(encodeUrl(entry.getKey()) + "=" + encodeUrl(entry.getValue()));
        }

        return formData.toString();
    }

    private static String encodeUrl(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static String base64UrlEncoder(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    }
}
