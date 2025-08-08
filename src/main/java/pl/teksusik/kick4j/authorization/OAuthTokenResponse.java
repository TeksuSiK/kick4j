package pl.teksusik.kick4j.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuthTokenResponse {
    private final String accessToken;
    private final String tokenType;
    private final String refreshToken;
    private final int expiresIn;
    private final String scope;

    public OAuthTokenResponse(@JsonProperty("access_token") String accessToken,
                              @JsonProperty("token_type") String tokenType,
                              @JsonProperty("refresh_token") String refreshToken,
                              @JsonProperty("expires_in") int expiresIn,
                              @JsonProperty("scope") String scope) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }
}
