package pl.teksusik.kick4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;
import pl.teksusik.kick4j.api.ApiResponse;
import pl.teksusik.kick4j.api.ApiResponseDeserializer;
import pl.teksusik.kick4j.authorization.AuthorizationClient;
import pl.teksusik.kick4j.authorization.RefreshTokenStore;
import pl.teksusik.kick4j.categories.CategoriesClient;
import pl.teksusik.kick4j.channels.ChannelsClient;
import pl.teksusik.kick4j.chat.ChatClient;
import pl.teksusik.kick4j.events.EventsClient;
import pl.teksusik.kick4j.events.handler.BuiltInKickWebhookHandler;
import pl.teksusik.kick4j.events.handler.KickEventDispatcher;
import pl.teksusik.kick4j.events.handler.KickSignatureVerifier;
import pl.teksusik.kick4j.livestreams.LivestreamsClient;
import pl.teksusik.kick4j.moderation.ModerationClient;
import pl.teksusik.kick4j.publicKey.PublicKeyClient;
import pl.teksusik.kick4j.users.UsersClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;

public class KickClient {
    private final AuthorizationClient authorizationClient;
    private final CategoriesClient categoriesClient;
    private final UsersClient usersClient;
    private final ChannelsClient channelsClient;
    private final ChatClient chatClient;
    private final ModerationClient moderationClient;
    private final LivestreamsClient livestreamsClient;
    private final PublicKeyClient publicKeyClient;
    private final EventsClient eventsClient;
    private final KickSignatureVerifier signatureVerifier;
    private final KickEventDispatcher eventDispatcher;

    private HttpServer httpServer;

    public KickClient(KickConfiguration configuration) {
        HttpClient httpClient = HttpClient.newHttpClient();

        SimpleModule serializerModule = new SimpleModule()
                .addDeserializer(ApiResponse.class, new ApiResponseDeserializer<>());
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(new JavaTimeModule())
                .registerModule(serializerModule);

        this.authorizationClient = new AuthorizationClient(httpClient, objectMapper, configuration, configuration.getTokenStore());
        this.categoriesClient = new CategoriesClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.usersClient = new UsersClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.channelsClient = new ChannelsClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.chatClient = new ChatClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.moderationClient = new ModerationClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.livestreamsClient = new LivestreamsClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.publicKeyClient = new PublicKeyClient(httpClient, objectMapper, configuration, this.authorizationClient);
        this.eventsClient = new EventsClient(httpClient, objectMapper, configuration, this.authorizationClient);

        this.signatureVerifier = new KickSignatureVerifier(this.publicKeyClient);
        this.eventDispatcher = new KickEventDispatcher(objectMapper);
    }

    public void startWebhookReceiver(String path, int port) {
        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            this.httpServer.createContext(path, new BuiltInKickWebhookHandler(this.signatureVerifier, this.eventDispatcher));
            this.httpServer.setExecutor(null);
            this.httpServer.start();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to start web server", exception);
        }
    }

    public void stopWebhookReceiver() {
        if (this.httpServer == null) {
            throw new IllegalStateException("Web server is not running");
        }

        this.httpServer.stop(100);
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

    public ModerationClient moderation() {
        return moderationClient;
    }

    public LivestreamsClient livestreams() {
        return livestreamsClient;
    }

    public PublicKeyClient publicKey() {
        return publicKeyClient;
    }

    public EventsClient events() {
        return eventsClient;
    }

    public KickSignatureVerifier signatureVerifier() {
        return signatureVerifier;
    }

    public KickEventDispatcher eventDispatcher() {
        return eventDispatcher;
    }
}
