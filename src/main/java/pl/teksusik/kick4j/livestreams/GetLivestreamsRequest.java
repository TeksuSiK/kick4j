package pl.teksusik.kick4j.livestreams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetLivestreamsRequest {
    @Singular("broadcasterUserId")
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

    public enum Sort {
        VIEWER_COUNT, STARTED_AT;

        @JsonValue
        public String toLower() {
            return name().toLowerCase();
        }
    }
}
