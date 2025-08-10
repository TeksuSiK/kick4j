package pl.teksusik.kick4j.events.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.teksusik.kick4j.events.type.KickEvent;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KickEventDispatcher {
    private final Map<String, Class<? extends KickEvent>> eventTypeToClass = new HashMap<>();
    private final Map<Class<? extends KickEvent>, List<KickEventListener<?>>> handlers = new HashMap<>();

    private final ObjectMapper mapper;

    public KickEventDispatcher(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <E extends KickEvent> void registerListener(Class<E> eventClass, KickEventListener<E> listener) {
        String eventType = invokeStaticStringMethod(eventClass, "getEventType");
        String eventVersion = invokeStaticStringMethod(eventClass, "getEventVersion");
        String key = produceKey(eventType, eventVersion);

        if (!this.eventTypeToClass.containsKey(key)) {
            this.eventTypeToClass.put(key, eventClass);
        }

        this.handlers.computeIfAbsent(eventClass, k -> new ArrayList<>())
                .add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends KickEvent> void dispatch(String eventType, String version, String payload) {
        Class<? extends KickEvent> clazz = this.eventTypeToClass.get(produceKey(eventType, version));
        if (clazz == null) {
            return;
        }

        KickEvent event = this.deserialize(payload, clazz);
        List<KickEventListener<?>> handlers = this.handlers.get(clazz);
        if (handlers == null || handlers.isEmpty()) {
            return;
        }

        for (KickEventListener<?> listener : handlers) {
            ((KickEventListener<KickEvent>) listener).handle(event);
        }
    }

    private KickEvent deserialize(String payload, Class<? extends KickEvent> clazz) {
        try {
            return this.mapper.readValue(payload, clazz);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to deserialize Kick event", exception);
        }
    }

    private static String produceKey(String eventType, String version) {
        return eventType + ":" + version;
    }

    private static String invokeStaticStringMethod(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getMethod(methodName);
            Object res = method.invoke(null);
            return (String) res;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke static method " + methodName + " on " + clazz.getName(), e);
        }
    }
}
