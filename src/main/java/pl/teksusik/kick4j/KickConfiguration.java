package pl.teksusik.kick4j;

import pl.teksusik.kick4j.authorization.RefreshTokenStore;

public final class KickConfiguration {
    private final RefreshTokenStore tokenStore;

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private final String oAuthHost;
    private final String authorizationEndpoint;
    private final String tokenEndpoint;
    private final String baseUrl;
    private final String categories;
    private final String categoriesId;
    private final String tokenIntrospect;
    private final String users;
    private final String channels;
    private final String chat;
    private final String moderation;
    private final String livestreams;
    private final String livestreamsStats;
    private final String publicKey;
    private final String events;

    private KickConfiguration(Builder builder) {
        this.tokenStore = builder.tokenStore;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.redirectUri = builder.redirectUri;
        this.oAuthHost = builder.oAuthHost;
        this.authorizationEndpoint = builder.authorizationEndpoint;
        this.tokenEndpoint = builder.tokenEndpoint;
        this.baseUrl = builder.baseUrl;
        this.categories = builder.categories;
        this.categoriesId = builder.categoriesId;
        this.tokenIntrospect = builder.tokenIntrospect;
        this.users = builder.users;
        this.channels = builder.channels;
        this.chat = builder.chat;
        this.moderation = builder.moderation;
        this.livestreams = builder.livestreams;
        this.livestreamsStats = builder.livestreamsStats;
        this.publicKey = builder.publicKey;
        this.events = builder.events;
    }

    public static Builder builder() {
        return new Builder();
    }

    public RefreshTokenStore getTokenStore() {
        return tokenStore;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getOAuthHost() {
        return oAuthHost;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCategories() {
        return categories;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public String getTokenIntrospect() {
        return tokenIntrospect;
    }

    public String getUsers() {
        return users;
    }

    public String getChannels() {
        return channels;
    }

    public String getChat() {
        return chat;
    }

    public String getModeration() {
        return moderation;
    }

    public String getLivestreams() {
        return livestreams;
    }

    public String getLivestreamsStats() {
        return livestreamsStats;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEvents() {
        return events;
    }

    public static final class Builder {
        private RefreshTokenStore tokenStore;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String oAuthHost = "https://id.kick.com";
        private String authorizationEndpoint = "/oauth/authorize";
        private String tokenEndpoint = "/oauth/token";
        private String baseUrl = "https://api.kick.com/public/v1";
        private String categories = "/categories";
        private String categoriesId = "/categories/{id}";
        private String tokenIntrospect = "/token/introspect";
        private String users = "/users";
        private String channels = "/channels";
        private String chat = "/chat";
        private String moderation = "/moderation/bans";
        private String livestreams = "/livestreams";
        private String livestreamsStats = "/livestreams/stats";
        private String publicKey = "/public-key";
        private String events = "/events/subscriptions";

        public Builder tokenStore(RefreshTokenStore tokenStore) {
            this.tokenStore = tokenStore;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder oAuthHost(String oAuthHost) {
            this.oAuthHost = oAuthHost;
            return this;
        }

        public Builder authorizationEndpoint(String authorizationEndpoint) {
            this.authorizationEndpoint = authorizationEndpoint;
            return this;
        }

        public Builder tokenEndpoint(String tokenEndpoint) {
            this.tokenEndpoint = tokenEndpoint;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder categories(String categories) {
            this.categories = categories;
            return this;
        }

        public Builder categoriesId(String categoriesId) {
            this.categoriesId = categoriesId;
            return this;
        }

        public Builder tokenIntrospect(String tokenIntrospect) {
            this.tokenIntrospect = tokenIntrospect;
            return this;
        }

        public Builder users(String users) {
            this.users = users;
            return this;
        }

        public Builder channels(String channels) {
            this.channels = channels;
            return this;
        }

        public Builder chat(String chat) {
            this.chat = chat;
            return this;
        }

        public Builder moderation(String moderation) {
            this.moderation = moderation;
            return this;
        }

        public Builder livestreams(String livestreams) {
            this.livestreams = livestreams;
            return this;
        }

        public Builder livestreamsStats(String livestreamsStats) {
            this.livestreamsStats = livestreamsStats;
            return this;
        }

        public Builder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public Builder events(String events) {
            this.events = events;
            return this;
        }

        public KickConfiguration build() {
            if (tokenStore == null) {
                throw new IllegalStateException("TokenStore is required");
            }

            if (clientId == null) {
                throw new IllegalStateException("ClientId is required");
            }

            if (clientSecret == null) {
                throw new IllegalStateException("ClientSecret is required");
            }

            if (redirectUri == null) {
                throw new IllegalStateException("RedirectUri is required");
            }

            return new KickConfiguration(this);
        }
    }
}
