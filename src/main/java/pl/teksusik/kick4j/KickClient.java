package pl.teksusik.kick4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.teksusik.kick4j.api.ApiResponse;
import pl.teksusik.kick4j.api.ApiResponseDeserializer;
import pl.teksusik.kick4j.authorization.AuthorizationClient;
import pl.teksusik.kick4j.categories.CategoriesClient;

import java.net.http.HttpClient;

public class KickClient {
    private final AuthorizationClient authorizationClient;
    private final CategoriesClient categoriesClient;

    public KickClient(String clientId, String clientSecret, String redirectUri) {
        HttpClient httpClient = HttpClient.newHttpClient();
        SimpleModule module = new SimpleModule()
                .addDeserializer(ApiResponse.class, new ApiResponseDeserializer<>());
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(module);

        this.authorizationClient = new AuthorizationClient(httpClient, objectMapper, clientId, clientSecret, redirectUri);
        this.categoriesClient = new CategoriesClient(httpClient, objectMapper, this.authorizationClient);
    }

    public AuthorizationClient authorization() {
        return authorizationClient;
    }

    public CategoriesClient categories() {
        return categoriesClient;
    }
}
