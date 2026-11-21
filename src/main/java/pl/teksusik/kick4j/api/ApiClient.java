package pl.teksusik.kick4j.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.KickConfiguration;
import pl.teksusik.kick4j.authorization.AuthMode;
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

    protected ApiClient(HttpClient httpClient, ObjectMapper mapper, KickConfiguration configuration, AuthorizationClient authorization) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.configuration = configuration;
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
        private String baseUrl;
        private boolean appAuth;
        private Map<String, Object> queryParams;
        private Object bodyObject;
        private Class<?> bodyClass;

        public RequestBuilder(String method, String path) {
            this.method = method;
            this.path = path;
            this.baseUrl = configuration.getBaseUrl();
            this.appAuth = configuration.getAuthMode() == AuthMode.APP;
        }

        /**
         * Overrides the base URL for this request (e.g. to target the {@code /public/v2} API).
         */
        public RequestBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Authenticates this request with an app access token (Client Credentials flow)
         * instead of the user access token. Use for endpoints that require an app token.
         */
        public RequestBuilder appAuth() {
            this.appAuth = true;
            return this;
        }

        /**
         * Authenticates this request with the user access token, overriding the configured
         * {@link KickConfiguration#getAuthMode() default auth mode} for this call.
         */
        public RequestBuilder userAuth() {
            this.appAuth = false;
            return this;
        }

        public RequestBuilder queryParams(Map<String, Object> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public RequestBuilder queryParams(Object queryParams) {
            if (queryParams == null) {
                return this;
            }

            Map<String, Object> map = mapper.convertValue(queryParams, new TypeReference<>() {});
            map.entrySet().removeIf(e -> e.getValue() == null);

            if (this.queryParams == null) {
                this.queryParams = map;
            } else {
                this.queryParams.putAll(map);
            }

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

        private HttpResponse<String> execute() throws IOException, InterruptedException {
            String url = buildUrl(this.baseUrl + this.path, this.queryParams);
            String accessToken = this.appAuth ? authorization.getAppAccessToken() : authorization.getAccessToken();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/json");

            if ("GET".equalsIgnoreCase(method)) {
                requestBuilder.GET();
            } else {
                String jsonBody = this.bodyObject == null ? "" : mapper.writeValueAsString(this.bodyObject);
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.method(this.method, HttpRequest.BodyPublishers.ofString(jsonBody));
            }

            return httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        }

        public <T> T send(TypeReference<ApiResponse<T>> typeRef) {
            try {
                HttpResponse<String> response = execute();
                String body = response.body();
                throwIfError(response.statusCode(), body);
                if (body == null || body.isEmpty()) {
                    return null;
                }

                ApiResponse<T> apiResponse = mapper.readValue(body, typeRef);
                return apiResponse.getData();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("API request interrupted", exception);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to send API request", exception);
            }
        }

        /**
         * Sends the request and deserializes the whole response body into {@code typeRef}
         * (rather than unwrapping only the {@code data} field). Used for paginated
         * responses that also carry a {@code pagination} object.
         */
        public <T> T sendRaw(TypeReference<T> typeRef) {
            try {
                HttpResponse<String> response = execute();
                String body = response.body();
                throwIfError(response.statusCode(), body);
                if (body == null || body.isEmpty()) {
                    return null;
                }

                return mapper.readValue(body, typeRef);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("API request interrupted", exception);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to send API request", exception);
            }
        }

        /**
         * Throws an {@link ApiException} for any 4xx/5xx response before the body is mapped to the
         * success model, so error envelopes such as {@code {"data": "AD_BREAK_ACTIVE", "message": "..."}}
         * keep their message and reason.
         */
        private void throwIfError(int statusCode, String body) {
            if (statusCode < 400) {
                return;
            }

            String message = body;
            String reason = null;
            if (body != null && !body.isEmpty()) {
                try {
                    JsonNode node = mapper.readTree(body);
                    if (node.isObject()) {
                        message = node.hasNonNull("message") ? node.get("message").asText() : null;
                        JsonNode data = node.get("data");
                        if (data != null && data.isTextual()) {
                            reason = data.asText();
                        }
                    }
                } catch (IOException ignored) {
                }
            }

            throw new ApiException(statusCode, message, reason);
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
