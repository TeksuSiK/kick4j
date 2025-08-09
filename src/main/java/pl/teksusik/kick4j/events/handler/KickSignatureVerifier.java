package pl.teksusik.kick4j.events.handler;

import pl.teksusik.kick4j.publicKey.PublicKeyClient;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KickSignatureVerifier {
    private final PublicKeyClient publicKeyClient;

    public KickSignatureVerifier(PublicKeyClient publicKeyClient) {
        this.publicKeyClient = publicKeyClient;
    }

    public boolean isValidSignature(String messageId, String messageTimestamp, String body, String signature) {
        try {
            String pem = this.publicKeyClient.getPublicKey();
            PublicKey key = parsePublicKey(pem);

            byte[] signedData = buildSignedData(messageId, messageTimestamp, body);
            byte[] signatureBytes = decodeBase64(signature);

            return this.verifySignature(key, signedData, signatureBytes);
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean verifySignature(PublicKey publicKey, byte[] data, byte[] signatureBytes) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }

    private static PublicKey parsePublicKey(String pem) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String stripped = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(stripped);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private static byte[] buildSignedData(String messageId, String timestamp, String body) {
        String data = messageId + "." + timestamp + "." + body;
        return data.getBytes(StandardCharsets.UTF_8);
    }

    private byte[] decodeBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
