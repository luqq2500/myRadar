package adversity;

import java.util.Optional;

public interface AdversityRepository {
    Optional<String> getMain(String mainAdversity);
    Optional<String> getSub(String subAdversity);
}
