package event.api;

import event.SubAdversity;

import java.util.UUID;

public interface EventPoster {
    void post(UUID userId, SubAdversity subAdversity, String description);
}
