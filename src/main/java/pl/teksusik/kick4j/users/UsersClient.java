package pl.teksusik.kick4j.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

/**
 * Client for interacting with user-related endpoints of the Kick API.
 * <p>
 * Provides methods for retrieving user information and performing token introspection
 * as defined by the OAuth 2.0 Token Introspection specification
 * (<a href="https://datatracker.ietf.org/doc/html/rfc7662">RFC 7662</a>).
 * </p>
 */
public class UsersClient extends ApiClient {
    /**
     * Creates a new instance of {@code UsersClient}.
     *
     * @param httpClient     the HTTP client to use for API requests
     * @param mapper         the Jackson {@link ObjectMapper} for JSON serialization/deserialization
     * @param authorization  the {@link AuthorizationClient} for managing API access tokens
     */
    public UsersClient(HttpClient httpClient, ObjectMapper mapper, AuthorizationClient authorization) {
        super(httpClient, mapper, authorization);
    }

    /**
     * Performs token introspection to obtain metadata about the current access token.
     * <p>
     * This method sends the current access token (provided via the {@code Authorization} header)
     * to Kick’s token introspection endpoint, which conforms to the
     * <a href="https://datatracker.ietf.org/doc/html/rfc7662">OAuth 2.0 Token Introspection</a> specification.
     * </p>
     * <p>
     * The introspection response will indicate whether the token is active. If
     * {@code active=false}, no additional information will be included in the response.
     * When {@code active=true}, the response may contain additional claims about the token
     * such as its scope, expiration, and the user it is associated with.
     * </p>
     *
     * @return a {@link TokenIntrospect} object containing the token's status and associated metadata
     */
    public TokenIntrospect tokenIntrospect() {
        return this.post(this.configuration.getTokenIntrospect())
                .send(new TypeReference<>() {});
    }

    /**
     * Retrieves information about the currently authenticated user.
     * <p>
     * Internally, this method calls {@link #getUsers(int... ids)} with the ID of the
     * current user obtained from the token context, and returns the first result.
     * </p>
     *
     * @return a {@link User} object representing the current user
     */
    public User getCurrentUser() {
        return this.getUsers().getFirst();
    }

    /**
     * Retrieves information about multiple users by their IDs.
     *
     * @param ids one or more user IDs
     * @return a list of {@link User} objects corresponding to the given IDs
     */
    public List<User> getUsers(int... ids) {
        return this.get(this.configuration.getUsers())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }

    /**
     * Retrieves information about multiple users by their IDs.
     *
     * @param ids a list of user IDs
     * @return a list of {@link User} objects corresponding to the given IDs
     */
    public List<User> getUsers(List<Integer> ids) {
        return this.get(this.configuration.getUsers())
                .queryParams(Map.of("id", ids))
                .send(new TypeReference<>() {});
    }
}
