package adversity.spi;

import adversity.Adversity;

import java.util.UUID;

public interface AdversityRepository {
    void add(Adversity adversity);
    Adversity get(UUID adversityId);
    Adversity getFirst();
}
