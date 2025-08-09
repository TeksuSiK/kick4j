package pl.teksusik.kick4j.channels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class ChannelsClient extends ApiClient {
    public ChannelsClient(HttpClient httpClient, ObjectMapper mapper, AuthorizationClient authorization) {
        super(httpClient, mapper, authorization);
    }

    public Channel getCurrentChannel() {
        List<Channel> channels = this.get(this.configuration.getChannels())
                .send(new TypeReference<>() {});
        return channels.getFirst();
    }

    public List<Channel> getChannels(int... broadcasterUserIds) {
        return this.get(this.configuration.getChannels())
                .queryParams(Map.of("broadcaster_user_id", broadcasterUserIds))
                .send(new TypeReference<>() {});
    }

    public List<Channel> getChannelsByUserIds(List<Integer> broadcasterUserIds) {
        return this.get(this.configuration.getChannels())
                .queryParams(Map.of("broadcaster_user_id", broadcasterUserIds))
                .send(new TypeReference<>() {});
    }

    public Channel getChannel(int broadcasterUserId) {
        return this.getChannels(broadcasterUserId).getFirst();
    }

    public List<Channel> getChannels(String... slugs) {
        return this.get(this.configuration.getChannels())
                .queryParams(Map.of("slug", slugs))
                .send(new TypeReference<>() {});
    }


    public List<Channel> getChannelsBySlugs(List<String> slugs) {
        return this.get(this.configuration.getChannels())
                .queryParams(Map.of("slug", slugs))
                .send(new TypeReference<>() {});
    }

    public Channel getChannel(String slug) {
        return this.getChannels(slug).getFirst();
    }

    public void updateChannel(UpdateChannelRequest request) {
        this.patch(this.configuration.getChannels())
                .body(request)
                .send(new TypeReference<>() {});
    }
}
