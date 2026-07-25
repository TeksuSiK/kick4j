package pl.teksusik.kick4j.chat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.Map;

public class ChatClient extends ApiClient {
    public ChatClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    public PostChatMessageResponse postChatMessage(PostChatMessageRequest request) {
        return this.post(this.configuration.getChat())
                .body(request)
                .send(new TypeReference<>() {});
    }

    /**
     * Removes a message from a channel's chat.
     * Requires the {@code moderation:chat_message:manage} scope.
     *
     * @param messageId the UUID of the message to delete
     */
    public void deleteChatMessage(String messageId) {
        this.delete(this.configuration.getChatMessage())
                .pathParams(Map.of("message_id", messageId))
                .send(new TypeReference<>() {});
    }
}
