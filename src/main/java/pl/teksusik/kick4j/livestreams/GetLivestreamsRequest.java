package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLivestreamsRequest {
    private final List<Integer> broadcasterUserId;
    private final Integer category;
    private final String language;
    private final Integer limit;
    private final Sort sort;

    public GetLivestreamsRequest(List<Integer> broadcasterUserId, Integer category, String language, Integer limit, Sort sort) {
        this.broadcasterUserId = broadcasterUserId;
        this.category = category;
        this.language = language;
        this.limit = limit;
        this.sort = sort;
    }

    public List<Integer> getBroadcasterUserId() {
        return broadcasterUserId;
    }

    public Integer getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getLimit() {
        return limit;
    }

    public Sort getSort() {
        return sort;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Integer> broadcasterUserId;
        private Integer category;
        private String language;
        private Integer limit;
        private Sort sort;

        public Builder broadcasterUserId(List<Integer> broadcasterUserId) {
            this.broadcasterUserId = broadcasterUserId;
            return this;
        }

        public Builder broadcasterUserId(Integer broadcasterUserId) {
            this.broadcasterUserId = List.of(broadcasterUserId);
            return this;
        }

        public Builder category(Integer category) {
            this.category = category;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder sort(Sort sort) {
            this.sort = sort;
            return this;
        }

        public GetLivestreamsRequest build() {
            return new GetLivestreamsRequest(broadcasterUserId, category, language, limit, sort);
        }
    }

    public enum Sort {
        VIEWER_COUNT, STARTED_AT;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
