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
    private final String revokeEndpoint;
    private final String introspectEndpoint;
    private final String baseUrl;
    private final String baseUrlV2;
    private final boolean defaultAppAuth;
    private final String categories;
    private final String categoriesId;
    private final String tokenIntrospect;
    private final String users;
    private final String channels;
    private final String chat;
    private final String chatMessage;
    private final String moderation;
    private final String livestreams;
    private final String livestreamsStats;
    private final String usersLivestreams;
    private final String publicKey;
    private final String events;
    private final String channelsRewards;
    private final String channelsRewardsId;
    private final String channelsRewardsRedemptions;
    private final String channelsRewardsRedemptionsAccept;
    private final String channelsRewardsRedemptionsReject;
    private final String kicksLeaderboard;
    private final String dropsClaims;

    private KickConfiguration(Builder builder) {
        this.tokenStore = builder.tokenStore;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.redirectUri = builder.redirectUri;
        this.oAuthHost = builder.oAuthHost;
        this.authorizationEndpoint = builder.authorizationEndpoint;
        this.tokenEndpoint = builder.tokenEndpoint;
        this.revokeEndpoint = builder.revokeEndpoint;
        this.introspectEndpoint = builder.introspectEndpoint;
        this.baseUrl = builder.baseUrl;
        this.baseUrlV2 = builder.baseUrlV2;
        this.defaultAppAuth = builder.defaultAppAuth;
        this.categories = builder.categories;
        this.categoriesId = builder.categoriesId;
        this.tokenIntrospect = builder.tokenIntrospect;
        this.users = builder.users;
        this.channels = builder.channels;
        this.chat = builder.chat;
        this.chatMessage = builder.chatMessage;
        this.moderation = builder.moderation;
        this.livestreams = builder.livestreams;
        this.livestreamsStats = builder.livestreamsStats;
        this.usersLivestreams = builder.usersLivestreams;
        this.publicKey = builder.publicKey;
        this.events = builder.events;
        this.channelsRewards = builder.channelsRewards;
        this.channelsRewardsId = builder.channelsRewardsId;
        this.channelsRewardsRedemptions = builder.channelsRewardsRedemptions;
        this.channelsRewardsRedemptionsAccept = builder.channelsRewardsRedemptionsAccept;
        this.channelsRewardsRedemptionsReject = builder.channelsRewardsRedemptionsReject;
        this.kicksLeaderboard = builder.kicksLeaderboard;
        this.dropsClaims = builder.dropsClaims;
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

    public String getRevokeEndpoint() {
        return revokeEndpoint;
    }

    public String getIntrospectEndpoint() {
        return introspectEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBaseUrlV2() {
        return baseUrlV2;
    }

    /**
     * Whether requests use an app access token (Client Credentials flow) by default,
     * instead of the user access token. Individual requests can still override this.
     */
    public boolean isDefaultAppAuth() {
        return defaultAppAuth;
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

    public String getChatMessage() {
        return chatMessage;
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

    public String getUsersLivestreams() {
        return usersLivestreams;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEvents() {
        return events;
    }

    public String getChannelsRewards() {
        return channelsRewards;
    }

    public String getChannelsRewardsId() {
        return channelsRewardsId;
    }

    public String getChannelsRewardsRedemptions() {
        return channelsRewardsRedemptions;
    }

    public String getChannelsRewardsRedemptionsAccept() {
        return channelsRewardsRedemptionsAccept;
    }

    public String getChannelsRewardsRedemptionsReject() {
        return channelsRewardsRedemptionsReject;
    }

    public String getKicksLeaderboard() {
        return kicksLeaderboard;
    }

    public String getDropsClaims() {
        return dropsClaims;
    }

    public static final class Builder {
        private RefreshTokenStore tokenStore;
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private String oAuthHost = "https://id.kick.com";
        private String authorizationEndpoint = "/oauth/authorize";
        private String tokenEndpoint = "/oauth/token";
        private String revokeEndpoint = "/oauth/revoke";
        private String introspectEndpoint = "/oauth/token/introspect";
        private String baseUrl = "https://api.kick.com/public/v1";
        private String baseUrlV2 = "https://api.kick.com/public/v2";
        private boolean defaultAppAuth = false;
        private String categories = "/categories";
        private String categoriesId = "/categories/{id}";
        private String tokenIntrospect = "/token/introspect";
        private String users = "/users";
        private String channels = "/channels";
        private String chat = "/chat";
        private String chatMessage = "/chat/{message_id}";
        private String moderation = "/moderation/bans";
        private String livestreams = "/livestreams";
        private String livestreamsStats = "/livestreams/stats";
        private String usersLivestreams = "/users/livestreams";
        private String publicKey = "/public-key";
        private String events = "/events/subscriptions";
        private String channelsRewards = "/channels/rewards";
        private String channelsRewardsId = "/channels/rewards/{id}";
        private String channelsRewardsRedemptions = "/channels/rewards/redemptions";
        private String channelsRewardsRedemptionsAccept = "/channels/rewards/redemptions/accept";
        private String channelsRewardsRedemptionsReject = "/channels/rewards/redemptions/reject";
        private String kicksLeaderboard = "/kicks/leaderboard";
        private String dropsClaims = "/drops/claims";

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

        public Builder revokeEndpoint(String revokeEndpoint) {
            this.revokeEndpoint = revokeEndpoint;
            return this;
        }

        public Builder introspectEndpoint(String introspectEndpoint) {
            this.introspectEndpoint = introspectEndpoint;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder baseUrlV2(String baseUrlV2) {
            this.baseUrlV2 = baseUrlV2;
            return this;
        }

        /**
         * Makes every request default to an app access token (Client Credentials flow)
         * instead of the user access token. Ideal for server-to-server / app-only usage.
         * Individual requests may still override this per call.
         */
        public Builder defaultAppAuth(boolean defaultAppAuth) {
            this.defaultAppAuth = defaultAppAuth;
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

        public Builder chatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
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

        public Builder usersLivestreams(String usersLivestreams) {
            this.usersLivestreams = usersLivestreams;
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

        public Builder channelsRewards(String channelsRewards) {
            this.channelsRewards = channelsRewards;
            return this;
        }

        public Builder channelsRewardsId(String channelsRewardsId) {
            this.channelsRewardsId = channelsRewardsId;
            return this;
        }

        public Builder channelsRewardsRedemptions(String channelsRewardsRedemptions) {
            this.channelsRewardsRedemptions = channelsRewardsRedemptions;
            return this;
        }

        public Builder channelsRewardsRedemptionsAccept(String channelsRewardsRedemptionsAccept) {
            this.channelsRewardsRedemptionsAccept = channelsRewardsRedemptionsAccept;
            return this;
        }

        public Builder channelsRewardsRedemptionsReject(String channelsRewardsRedemptionsReject) {
            this.channelsRewardsRedemptionsReject = channelsRewardsRedemptionsReject;
            return this;
        }

        public Builder kicksLeaderboard(String kicksLeaderboard) {
            this.kicksLeaderboard = kicksLeaderboard;
            return this;
        }

        public Builder dropsClaims(String dropsClaims) {
            this.dropsClaims = dropsClaims;
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
