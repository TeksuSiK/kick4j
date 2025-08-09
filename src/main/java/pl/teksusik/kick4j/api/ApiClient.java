package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.AuthorizationClient;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

public abstract class ApiClient {
    protected final HttpClient httpClient;
    protected final ObjectMapper mapper;
    protected final KickConfiguration configuration;
    protected final AuthorizationClient authorization;

    protected ApiClient(HttpClient httpClient, ObjectMapper mapper, AuthorizationClient authorization) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.configuration = KickConfiguration.getConfiguration();
        this.authorization = authorization;
    }

    protected RequestBuilder get(String path) {
        return new RequestBuilder("GET", path);
    }

    protected RequestBuilder post(String path) {
        return new RequestBuilder("POST", path);
    }

    protected RequestBuilder patch(String path) {
        return new RequestBuilder("PATCH", path);
    }

    protected RequestBuilder delete(String path) {
        return new RequestBuilder("DELETE", path);
    }

    public class RequestBuilder {
        private final String method;
        private String path;
        private Map<String, Object> queryParams;
        private Object bodyObject;
        private Class<?> bodyClass;

        public RequestBuilder(String method, String path) {
            this.method = method;
            this.path = path;
        }

        public RequestBuilder queryParams(Map<String, Object> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public RequestBuilder pathParams(Map<String, Object> params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String placeholder = "\\{" + entry.getKey() + "\\}";
                String encodedValue = encode(entry.getValue().toString());
                this.path = this.path.replaceAll(placeholder, encodedValue);
            }
            return this;
        }

        public RequestBuilder body(Object bodyObject) {
            this.bodyObject = bodyObject;
            return this;
        }

        public <T> T send(TypeReference<ApiResponse<T>> typeRef) {
            try {
                String url = buildUrl(configuration.getBaseUrl() + this.path, this.queryParams);
                HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + authorization.getAccessToken())
                        .header("Accept", "application/json");

                if ("GET".equalsIgnoreCase(method)) {
                    requestBuilder.GET();
                } else {
                    String jsonBody = this.bodyObject == null ? "" : mapper.writeValueAsString(this.bodyObject);
                    requestBuilder.header("Content-Type", "application/json");
                    requestBuilder.method(this.method, HttpRequest.BodyPublishers.ofString(jsonBody));
                }

                HttpResponse<String> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
                String body = response.body();
                if (body == null || body.isEmpty()) {
                    return null;
                }

                ApiResponse<T> apiResponse = mapper.readValue(response.body(), typeRef);
                if (!apiResponse.isSuccess() && response.statusCode() >= 400) {
                    throw new ApiException(response.statusCode(), apiResponse.getMessage());
                }

                return apiResponse.getData();
            } catch (IOException | InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Failed to send API request", exception);
            }
        }
    }

    private static String buildUrl(String path, Map<String, Object> queryParams) {
        if (queryParams == null) {
            return path;
        }

        StringJoiner joiner = new StringJoiner("&", path + "?", "");
        for (Map.Entry<String, Object> param : queryParams.entrySet()) {
            Object value = param.getValue();

            if (value instanceof Iterable<?>) {
                for (Object v : (Iterable<?>) value) {
                    joiner.add(encode(param.getKey()) + "=" + encode(String.valueOf(v)));
                }
            } else if (value != null && value.getClass().isArray()) {
                int length = Array.getLength(value);

                for (int i = 0; i < length; i++) {
                    Object v = Array.get(value, i);
                    joiner.add(encode(param.getKey()) + "=" + encode(String.valueOf(v)));
                }
            } else {
                joiner.add(encode(param.getKey()) + "=" + encode(String.valueOf(value)));
            }
        }

        return joiner.toString();
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
