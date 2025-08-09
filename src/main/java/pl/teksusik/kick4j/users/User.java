package pl.teksusik.kick4j.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final String email;
    private final String name;
    private final String profilePicture;
    private final int userId;

    @JsonCreator
    public User(@JsonProperty("email") String email,
                @JsonProperty("name") String name,
                @JsonProperty("profile_picture") String profilePicture,
                @JsonProperty("user_id") int userId) {
        this.email = email;
        this.name = name;
        this.profilePicture = profilePicture;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public int getUserId() {
        return userId;
    }
}
