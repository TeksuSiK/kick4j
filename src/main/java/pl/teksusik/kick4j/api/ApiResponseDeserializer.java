package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class ApiResponseDeserializer<T> extends JsonDeserializer<ApiResponse<T>> implements ContextualDeserializer {
    private JavaType valueType;

    public ApiResponseDeserializer(JavaType valueType) {
        this.valueType = valueType;
    }

    @Override
    public ApiResponse<T> deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
        ObjectCodec codec = parser.getCodec();
        JsonNode root = codec.readTree(parser);

        JsonNode dataNode = root.get("data");
        T data;

        if (dataNode == null || dataNode.isNull() || (dataNode.isObject() && dataNode.isEmpty())) {
            data = null;
        } else {
            ObjectMapper mapper = (ObjectMapper) codec;
            data = mapper.readerFor(valueType).readValue(dataNode.traverse(mapper));
        }

        String message = root.has("message") ? root.get("message").asText(null) : null;
        return new ApiResponse<>(data, message);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctx, BeanProperty property) {
        JavaType contextualType = (property != null) ? property.getType() : ctx.getContextualType();

        if (contextualType == null || !contextualType.hasGenericTypes()) {
            return this;
        }

        JavaType contentType = contextualType.containedType(0);
        return new ApiResponseDeserializer<>(contentType);
    }
}
