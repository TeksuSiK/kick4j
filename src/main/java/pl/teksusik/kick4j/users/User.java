package pl.teksusik.kick4j.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class User {
    private final String email;
    private final String name;
    private final String profilePicture;
    private final Integer userId;

    @JsonCreator
    public User(@JsonProperty("email") String email,
                @JsonProperty("name") String name,
                @JsonProperty("profile_picture") String profilePicture,
                @JsonProperty("user_id") Integer userId) {
        this.email = email;
        this.name = name;
        this.profilePicture = profilePicture;
        this.userId = userId;
    }
}
