package pl.teksusik.kick4j.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.api.ApiClient;
import pl.teksusik.kick4j.api.PaginatedResponse;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

public class CategoriesClient extends ApiClient {
    public CategoriesClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        super(httpClient, mapper, configuration, authorization);
    }

    /**
     * @deprecated The V1 categories endpoint is deprecated by Kick and will be removed.
     * Use {@link #getCategoriesV2(GetCategoriesV2Request)} instead.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public List<Category> getCategories(String query) {
        return this.getCategories(query, 1);
    }

    /**
     * @deprecated The V1 categories endpoint is deprecated by Kick and will be removed.
     * Use {@link #getCategoriesV2(GetCategoriesV2Request)} instead.
     */
    @Deprecated
    public List<Category> getCategories(String query, int page) {
        return this.get(this.configuration.getCategories())
                .queryParams(Map.of("q", query,
                        "page", page))
                .send(new TypeReference<>() {});
    }

    /**
     * @deprecated The V1 categories endpoint is deprecated by Kick and will be removed.
     * Use {@link #getCategoriesV2(GetCategoriesV2Request)} instead.
     */
    @Deprecated
    public Category getCategory(int categoryId) {
        return this.get(this.configuration.getCategoriesId())
                .pathParams(Map.of("id", categoryId))
                .send(new TypeReference<>() {});
    }

    /**
     * Lists categories via the paginated V2 endpoint, with default filters
     * (first page, API default limit).
     */
    public PaginatedResponse<CategoryWithTags> getCategoriesV2() {
        return this.getCategoriesV2(GetCategoriesV2Request.builder().build());
    }

    /**
     * Lists categories via the paginated V2 endpoint using the provided filters.
     * Use {@link PaginatedResponse#getPagination()} to page through results with a cursor.
     */
    public PaginatedResponse<CategoryWithTags> getCategoriesV2(GetCategoriesV2Request request) {
        return this.get(this.configuration.getCategories())
                .baseUrl(this.configuration.getBaseUrlV2())
                .queryParams(request.toQueryParams())
                .sendRaw(new TypeReference<>() {});
    }
}
