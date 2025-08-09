package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;

public class ChatClient extends ApiClient {
    public ChatClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    public PostChatMessageResponse postChatMessage(PostChatMessageRequest request) {
        return this.post(this.configuration.getChat())
                .body(request)
                .send(new TypeReference<>() {});
    }
}
