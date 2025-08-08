package pl.teksusik.kick4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;

public class KickClient {
    private final HttpClient httpClient;

    private final AuthorizationClient authorizationClient;

    public KickClient(String clientId, String clientSecret, String redirectUri) {
        this.httpClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        this.authorizationClient = new AuthorizationClient(this.httpClient, objectMapper, clientId, clientSecret, redirectUri);
    }

    public AuthorizationClient authorization() {
        return authorizationClient;
    }
}
