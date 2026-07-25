package pl.teksusik.kick4j.categories;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Filters for the paginated V2 categories endpoint.
 * <p>
 * The {@code names}, {@code tags} and {@code ids} arrays are sent as comma-separated
 * (CSV) query parameters, matching the API's {@code collectionFormat: csv}.
 */
public class GetCategoriesV2Request {
    private final String cursor;
    private final Integer limit;
    private final List<String> names;
    private final List<String> tags;
    private final List<Integer> ids;

    public GetCategoriesV2Request(String cursor, Integer limit, List<String> names, List<String> tags, List<Integer> ids) {
        this.cursor = cursor;
        this.limit = limit;
        this.names = names;
        this.tags = tags;
        this.ids = ids;
    }

    public String getCursor() {
        return cursor;
    }

    public Integer getLimit() {
        return limit;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Integer> getIds() {
        return ids;
    }

    /**
     * Builds the query parameter map, joining array filters into CSV values.
     */
    public Map<String, Object> toQueryParams() {
        Map<String, Object> params = new LinkedHashMap<>();
        if (this.cursor != null) {
            params.put("cursor", this.cursor);
        }
        if (this.limit != null) {
            params.put("limit", this.limit);
        }
        if (this.names != null && !this.names.isEmpty()) {
            params.put("name", String.join(",", this.names));
        }
        if (this.tags != null && !this.tags.isEmpty()) {
            params.put("tag", String.join(",", this.tags));
        }
        if (this.ids != null && !this.ids.isEmpty()) {
            params.put("id", this.ids.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String cursor;
        private Integer limit;
        private List<String> names;
        private List<String> tags;
        private List<Integer> ids;

        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder names(List<String> names) {
            this.names = names;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder ids(List<Integer> ids) {
            this.ids = ids;
            return this;
        }

        public GetCategoriesV2Request build() {
            return new GetCategoriesV2Request(cursor, limit, names, tags, ids);
        }
    }
}
