package event;

import geo.Coordinate;
import geo.api.GeoLocator;
import event.api.EventPoster;
import event.spi.EventRepository;
import user.User;
import user.UserRepository;

import java.util.UUID;

public class PostEvent implements EventPoster {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GeoLocator geoLocator;

    public PostEvent(UserRepository userRepository, EventRepository eventRepository, GeoLocator geoLocator) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.geoLocator = geoLocator;
    }

    @Override
    public void post(UUID userId, SubAdversity subAdversity, String description) {
        User user = userRepository.get(userId);
        Coordinate coordinate = geoLocator.getCoordinate();
        Event event = user.post(subAdversity, coordinate, new Description(description));
        eventRepository.add(event);
    }
}
