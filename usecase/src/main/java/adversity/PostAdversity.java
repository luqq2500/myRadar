package adversity;

import geo.Coordinate;
import geo.api.GeoLocator;
import adversity.api.AdversityPoster;
import adversity.spi.AdversityRepository;
import user.User;
import user.UserRepository;

import java.util.UUID;

public class PostAdversity implements AdversityPoster {
    private final UserRepository userRepository;
    private final AdversityRepository adversityRepository;
    private final GeoLocator geoLocator;

    public PostAdversity(UserRepository userRepository, AdversityRepository adversityRepository, GeoLocator geoLocator) {
        this.userRepository = userRepository;
        this.adversityRepository = adversityRepository;
        this.geoLocator = geoLocator;
    }

    @Override
    public void post(UUID userId, SubAdversity subAdversity, String description) {
        User user = userRepository.get(userId);
        Coordinate coordinate = geoLocator.getCoordinate();
        Adversity adversity = user.post(subAdversity, coordinate, new Description(description));
        adversityRepository.add(adversity);
    }
}
