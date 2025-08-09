package pl.teksusik.kick4j.publicKey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;

public class PublicKeyClient extends ApiClient {
    public PublicKeyClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    private PublicKey _getPublicKey() {
        return this.get(this.configuration.getPublicKey())
                .send(new TypeReference<>() {});
    }

    public String getPublicKey() {
        return this._getPublicKey().publicKey;
    }

    private static class PublicKey {
        private final String publicKey;

        @JsonCreator
        private PublicKey(@JsonProperty("public_key") String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
