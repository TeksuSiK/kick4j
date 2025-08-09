package pl.teksusik.kick4j.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenIntrospect {
    private final boolean active;
    private final String clientId;
    private final int exp;
    private final String scope;
    private final String tokenType;

    @JsonCreator
    public TokenIntrospect(@JsonProperty("active") boolean active,
                           @JsonProperty("client_id") String clientId,
                           @JsonProperty("exp") int exp,
                           @JsonProperty("scope") String scope,
                           @JsonProperty("token_type") String tokenType) {
        this.active = active;
        this.clientId = clientId;
        this.exp = exp;
        this.scope = scope;
        this.tokenType = tokenType;
    }

    public boolean isActive() {
        return active;
    }

    public String getClientId() {
        return clientId;
    }

    public int getExp() {
        return exp;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
