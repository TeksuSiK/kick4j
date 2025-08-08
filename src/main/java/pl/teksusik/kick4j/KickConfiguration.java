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
}
