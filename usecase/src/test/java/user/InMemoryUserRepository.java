package user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryUserRepository implements UserRepository {
    private List<User> users = new ArrayList<>();
    @Override
    public User get(UUID userId) {
        return users.stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
    }

    @Override
    public void add(User user) {
        users.add(user);
    }
}
