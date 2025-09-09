package user;

import java.util.UUID;

public interface UserRepository {
    User get(UUID userId);
    void add(User user);
}
