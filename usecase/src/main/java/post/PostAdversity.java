package post;

import adversity.AdversityDetails;
import adversity.AdversityRepository;
import adversity.SubAdversity;
import geo.Coordinate;
import geo.api.GeoLocator;
import post.api.AdversityPoster;
import post.spi.PostRepository;
import user.User;
import user.UserRepository;

import java.util.UUID;

public class PostAdversity implements AdversityPoster {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GeoLocator geoLocator;

    public PostAdversity(UserRepository userRepository, PostRepository postRepository, GeoLocator geoLocator) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.geoLocator = geoLocator;
    }

    @Override
    public void post(UUID userId, SubAdversity subAdversity, String description) {
        User user = userRepository.get(userId);
        Coordinate coordinate = geoLocator.getCoordinate();
        Post post = user.post(subAdversity, coordinate, new Description(description));
        postRepository.add(post);
    }
}
