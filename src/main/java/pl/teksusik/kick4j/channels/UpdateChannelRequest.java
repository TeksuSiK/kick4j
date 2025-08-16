package pl.teksusik.kick4j.channels;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateChannelRequest {
    private final Integer categoryId;
    private final List<String> customTags;
    private final String streamTitle;

    @JsonCreator
    public UpdateChannelRequest(
            @JsonProperty("category_id") Integer categoryId,
            @JsonProperty("custom_tags") List<String> customTags,
            @JsonProperty("stream_title") String streamTitle
    ) {
        this.categoryId = categoryId;
        this.customTags = customTags;
        this.streamTitle = streamTitle;
    }
}
