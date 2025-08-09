package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

/**
 * Client for interacting with category-related endpoints of the Kick API.
 * <p>
 * Provides methods to search for categories or retrieve category details
 * by their unique identifiers.
 * </p>
 */
public class CategoriesClient extends ApiClient {

    /**
     * Creates a new instance of {@code CategoriesClient}.
     *
     * @param httpClient     the HTTP client to use for requests
     * @param mapper         the Jackson {@link ObjectMapper} for JSON serialization/deserialization
     * @param authorization  the {@link AuthorizationClient} for managing API access tokens
     */
    public CategoriesClient(HttpClient httpClient, ObjectMapper mapper, AuthorizationClient authorization) {
        super(httpClient, mapper, authorization);
    }

    /**
     * Retrieves a list of categories matching the given search query.
     * <p>
     * This method defaults to returning the first page of results.
     * </p>
     *
     * @param query the search keyword to filter categories
     * @return a list of matching {@link Category} objects
     */
    public List<Category> getCategories(String query) {
        return this.getCategories(query, 1);
    }


    /**
     * Retrieves a paginated list of categories matching the given search query.
     *
     * @param query the search keyword to filter categories
     * @param page  the page number of results to retrieve (starting from 1)
     * @return a list of matching {@link Category} objects for the specified page
     */
    public List<Category> getCategories(String query, int page) {
        return this.get(this.configuration.getCategories())
                .queryParams(Map.of("q", query,
                        "page", page))
                .send(new TypeReference<>() {});
    }

    /**
     * Retrieves details of a specific category by its unique identifier.
     *
     * @param categoryId the unique ID of the category to retrieve
     * @return the {@link Category} object representing the category details
     */
    public Category getCategory(int categoryId) {
        return this.get(this.configuration.getCategoriesId())
                .pathParams(Map.of("id", categoryId))
                .send(new TypeReference<>() {});
    }
}
