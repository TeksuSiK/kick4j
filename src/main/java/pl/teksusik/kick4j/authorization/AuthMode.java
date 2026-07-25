package pl.teksusik.kick4j.authorization;

/**
 * The default token type used to authenticate API requests.
 *
 * <ul>
 *   <li>{@link #USER} — user access token (Authorization Code + PKCE flow); required for
 *       actions on behalf of a user (chat, moderation, channel updates, per-user events).</li>
 *   <li>{@link #APP} — app access token (Client Credentials flow); server-to-server access
 *       to publicly available data, no user login required.</li>
 * </ul>
 *
 * Individual requests may still override the configured mode.
 */
public enum AuthMode {
    USER, APP
}
