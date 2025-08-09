package pl.teksusik.kick4j.events.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BuiltInKickWebhookHandler implements HttpHandler {
    private final KickSignatureVerifier verifier;
    private final KickEventDispatcher dispatcher;

    public BuiltInKickWebhookHandler(KickSignatureVerifier verifier, KickEventDispatcher dispatcher) {
        this.verifier = verifier;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /*
         * Kick's API sends webhook requests with very fast retries and may send new requests
         * before the previous one is fully processed, which can cause duplicate notifications.
         * To avoid continuous retries from Kick, we accept all requests immediately by returning 200 OK,
         * even if the processing is still ongoing.
         *
         * In production environments, it is highly recommended to implement message deduplication and queuing
         * based on the unique message ID (Kick-Event-Message-Id) to handle duplicates reliably.
         */
        String requestMethod = exchange.getRequestMethod();

        Headers headers = exchange.getRequestHeaders();

        String messageId = headers.getFirst("Kick-Event-Message-Id");
        String subscriptionId = headers.getFirst("Kick-Event-Subscription-Id");
        String signature = headers.getFirst("Kick-Event-Signature");
        String messageTimestamp = headers.getFirst("Kick-Event-Message-Timestamp");
        String eventType = headers.getFirst("Kick-Event-Type");
        String eventVersion = headers.getFirst("Kick-Event-Version");

        String body = readRequestBody(exchange.getRequestBody());

        exchange.sendResponseHeaders(200, -1);
        exchange.getRequestBody().close();

        if (!"POST".equalsIgnoreCase(requestMethod)) {
            return;
        }

        if (messageId == null || subscriptionId == null || signature == null || messageTimestamp == null || eventType == null || eventVersion == null) {
            return;
        }

        if (!this.verifier.isValidSignature(messageId, messageTimestamp, body, signature)) {
            return;
        }

        this.dispatcher.dispatch(eventType, eventVersion, body);
    }

    private String readRequestBody(InputStream input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            return builder.toString().trim();
        }
    }
}
