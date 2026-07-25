package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Filters for the paginated V2 livestreams endpoint. The {@code categoryId} and
 * {@code languageCode} arrays are sent as repeated (multi) query parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLivestreamsV2Request {
    @JsonProperty("category_id")
    private final List<Integer> categoryId;
    @JsonProperty("language_code")
    private final List<String> languageCode;
    @JsonProperty("limit")
    private final Integer limit;
    @JsonProperty("cursor")
    private final String cursor;

    public GetLivestreamsV2Request(List<Integer> categoryId, List<String> languageCode, Integer limit, String cursor) {
        this.categoryId = categoryId;
        this.languageCode = languageCode;
        this.limit = limit;
        this.cursor = cursor;
    }

    public List<Integer> getCategoryId() {
        return categoryId;
    }

    public List<String> getLanguageCode() {
        return languageCode;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getCursor() {
        return cursor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Integer> categoryId;
        private List<String> languageCode;
        private Integer limit;
        private String cursor;

        public Builder categoryId(List<Integer> categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder languageCode(List<String> languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder cursor(String cursor) {
            this.cursor = cursor;
            return this;
        }

        public GetLivestreamsV2Request build() {
            return new GetLivestreamsV2Request(categoryId, languageCode, limit, cursor);
        }
    }
}
