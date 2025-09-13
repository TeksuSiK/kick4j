package pl.teksusik.kick4j.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import pl.teksusik.kick4j.events.handler.KickEventDispatcher;
import pl.teksusik.kick4j.events.handler.KickEventListener;
import pl.teksusik.kick4j.events.type.KickEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class KickEventDispatcherTest {
    protected KickEventDispatcher eventDispatcher;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .registerModule(new JavaTimeModule());

        this.eventDispatcher = new KickEventDispatcher(objectMapper);
    }

    protected <E extends KickEvent> E dispatchAndCapture(
            Class<E> eventClass, String eventType, String version, String payload) {

        KickEventListener<E> listener = mock(KickEventListener.class);
        this.eventDispatcher.registerListener(eventClass, listener);

        this.eventDispatcher.dispatch(eventType, version, payload);

        ArgumentCaptor<E> captor = ArgumentCaptor.forClass(eventClass);
        verify(listener, times(1)).handle(captor.capture());

        return captor.getValue();
    }
}
