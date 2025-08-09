package pl.teksusik.kick4j;

public class KickConfiguration {
    private static KickConfiguration configuration;

    private KickConfiguration() {
    }

    public static KickConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = new KickConfiguration();
        }

        return configuration;
    }

    private String oAuthHost = "https://id.kick.com";
    private String authorizationEndpoint = "/oauth/authorize";
    private String tokenEndpoint = "/oauth/token";

    private String baseUrl = "https://api.kick.com/public/v1";

    // Categories
    private String categories = "/categories";
    private String categoriesId = "/categories/{id}";

    // Users
    private String tokenIntrospect = "/token/introspect";
    private String users = "/users";

    // Channels
    private String channels = "/channels";

    // Chat
    private String chat = "/chat";

    // Moderation
    private String moderation = "/moderation/bans";

    public String getOAuthHost() {
        return oAuthHost;
    }

    public void setOAuthHost(String oAuthHost) {
        this.oAuthHost = oAuthHost;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getTokenIntrospect() {
        return tokenIntrospect;
    }

    public void setTokenIntrospect(String tokenIntrospect) {
        this.tokenIntrospect = tokenIntrospect;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getModeration() {
        return moderation;
    }

    public void setModeration(String moderation) {
        this.moderation = moderation;
    }
}
