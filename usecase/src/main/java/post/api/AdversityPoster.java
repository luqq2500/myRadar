package post.api;

import adversity.SubAdversity;

import java.util.UUID;

public interface AdversityPoster {
    void post(UUID userId, SubAdversity subAdversity, String description);
}
