package adversity;

import adversity.spi.AdversityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryAdversityRepository implements AdversityRepository {
    private final List<Adversity> adversities = new ArrayList<>();
    @Override
    public void add(Adversity adversity) {
        adversities.add(adversity);
    }

    @Override
    public Adversity get(UUID adversityId) {
        return adversities.stream()
                .filter(adversity -> adversity.getId().equals(adversityId)).findFirst().orElse(null);
    }

    @Override
    public Adversity getFirst() {
        return adversities.getFirst();
    }
}
