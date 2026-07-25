package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LivestreamUser {
    private final Integer id;
    private final String profilePicture;
    private final String username;

    @JsonCreator
    public LivestreamUser(@JsonProperty("id") Integer id,
                          @JsonProperty("profile_picture") String profilePicture,
                          @JsonProperty("username") String username) {
        this.id = id;
        this.profilePicture = profilePicture;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUsername() {
        return username;
    }
}
