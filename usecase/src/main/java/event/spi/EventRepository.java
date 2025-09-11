package event.spi;

import event.Event;

import java.util.UUID;

public interface EventRepository {
    void add(Event event);
    Event get(UUID adversityId);
    Event getFirst();
}
