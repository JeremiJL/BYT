package emptio.serialization;

import emptio.domain.Repository;
import emptio.domain.user.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements Repository<User> {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User add(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User find(Integer userId) {
        return users.get(userId);
    }
}
