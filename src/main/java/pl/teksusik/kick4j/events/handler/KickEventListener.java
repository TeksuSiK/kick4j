package pl.teksusik.kick4j.events.handler;

import pl.teksusik.kick4j.events.type.KickEvent;

public interface KickEventListener<T extends KickEvent> {
    void handle(T event);
}
