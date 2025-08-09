package pl.teksusik.kick4j.channels;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public List<String> getCustomTags() {
        return customTags;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer categoryId;
        private List<String> customTags;
        private String streamTitle;

        public Builder categoryId(Integer categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder customTags(List<String> customTags) {
            this.customTags = customTags;
            return this;
        }

        public Builder streamTitle(String streamTitle) {
            this.streamTitle = streamTitle;
            return this;
        }

        public UpdateChannelRequest build() {
            return new UpdateChannelRequest(categoryId, customTags, streamTitle);
        }
    }
}