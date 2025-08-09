package pl.teksusik.kick4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import pl.teksusik.kick4j.api.ApiResponse;
import pl.teksusik.kick4j.api.ApiResponseDeserializer;
import pl.teksusik.kick4j.authorization.AuthorizationClient;
import pl.teksusik.kick4j.categories.CategoriesClient;
import pl.teksusik.kick4j.channels.ChannelsClient;
import pl.teksusik.kick4j.chat.ChatClient;
import pl.teksusik.kick4j.users.UsersClient;

import java.net.http.HttpClient;

public class KickClient {
    private final AuthorizationClient authorizationClient;
    private final CategoriesClient categoriesClient;
    private final UsersClient usersClient;
    private final ChannelsClient channelsClient;
    private final ChatClient chatClient;

    public KickClient(String clientId, String clientSecret, String redirectUri) {
        HttpClient httpClient = HttpClient.newHttpClient();
        SimpleModule module = new SimpleModule()
                .addDeserializer(ApiResponse.class, new ApiResponseDeserializer<>());
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(module);

        this.authorizationClient = new AuthorizationClient(httpClient, objectMapper, clientId, clientSecret, redirectUri);
        this.categoriesClient = new CategoriesClient(httpClient, objectMapper, this.authorizationClient);
        this.usersClient = new UsersClient(httpClient, objectMapper, this.authorizationClient);
        this.channelsClient = new ChannelsClient(httpClient, objectMapper, this.authorizationClient);
        this.chatClient = new ChatClient(httpClient, objectMapper, this.authorizationClient);
    }

    public AuthorizationClient authorization() {
        return authorizationClient;
    }

    public CategoriesClient categories() {
        return categoriesClient;
    }

    public UsersClient users() {
        return usersClient;
    }

    public ChannelsClient channels() {
        return channelsClient;
    }

    public ChatClient chat() {
        return chatClient;
    }
}
