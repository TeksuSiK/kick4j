package pl.teksusik.kick4j.authorization;

public interface RefreshTokenStore {
    String getRefreshToken();
    void notifyRefreshTokenRoll(String newRefreshToken);
}
