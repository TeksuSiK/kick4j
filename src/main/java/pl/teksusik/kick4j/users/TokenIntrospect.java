package pl.teksusik.kick4j.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenIntrospect {
    private final Boolean active;
    private final String clientId;
    private final Integer exp;
    private final String scope;
    private final String tokenType;

    @JsonCreator
    public TokenIntrospect(@JsonProperty("active") Boolean active,
                           @JsonProperty("client_id") String clientId,
                           @JsonProperty("exp") Integer exp,
                           @JsonProperty("scope") String scope,
                           @JsonProperty("token_type") String tokenType) {
        this.active = active;
        this.clientId = clientId;
        this.exp = exp;
        this.scope = scope;
        this.tokenType = tokenType;
    }

    public Boolean isActive() {
        return active;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getExp() {
        return exp;
    }

    public String getScope() {
        return scope;
    }

    public String getTokenType() {
        return tokenType;
    }
}
