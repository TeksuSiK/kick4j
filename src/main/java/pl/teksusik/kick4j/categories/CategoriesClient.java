package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class CategoriesClient extends ApiClient {
    public CategoriesClient(HttpClient httpClient, ObjectMapper mapper, AuthorizationClient authorization) {
        super(httpClient, mapper, authorization);
    }

    public List<Category> getCategories(String query) {
        return this.getCategories(query, 1);
    }

    public List<Category> getCategories(String query, int page) {
        return this.get(this.configuration.getCategoriesUrl())
                .queryParams(Map.of("q", query,
                        "page", page))
                .send(new TypeReference<>() {});
    }

    public Category getCategory(int categoryId) {
        return this.get(this.configuration.getCategoriesIdUrl())
                .pathParams(Map.of("id", categoryId))
                .send(new TypeReference<>() {});
    }
}
