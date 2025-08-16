package pl.teksusik.kick4j.authorization;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@AllArgsConstructor
public class FileRefreshTokenStore implements RefreshTokenStore {
    private final Path path;

    @Override
    public String getRefreshToken() {
        try {
            return Files.readString(this.path);
        } catch (IOException exception) {
            throw new RuntimeException("Couldn't read refresh token", exception);
        }
    }

    @Override
    public void notifyRefreshTokenRoll(String newRefreshToken) {
        try {
            Files.writeString(this.path, newRefreshToken);
        } catch (IOException exception) {
            throw new RuntimeException("Couldn't write refresh token", exception);
        }
    }
}
