package emptio.serialization;

import emptio.domain.user.User;
import emptio.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public User add(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User find(User user) {
        return null;
    }
}
