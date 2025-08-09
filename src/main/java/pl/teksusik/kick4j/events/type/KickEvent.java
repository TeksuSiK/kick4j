package pl.teksusik.kick4j.events.type;

public abstract class KickEvent {
    public static String getEventType() {
        throw new UnsupportedOperationException();
    }

    public static String getEventVersion() {
        throw new UnsupportedOperationException();
    }
}
