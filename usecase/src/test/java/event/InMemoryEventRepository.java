package event;

import event.spi.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryEventRepository implements EventRepository {
    private final List<Event> adversities = new ArrayList<>();
    @Override
    public void add(Event event) {
        adversities.add(event);
    }

    @Override
    public Event get(UUID adversityId) {
        return adversities.stream()
                .filter(adversity -> adversity.getId().equals(adversityId)).findFirst().orElse(null);
    }

    @Override
    public Event getFirst() {
        return adversities.getFirst();
    }
}
