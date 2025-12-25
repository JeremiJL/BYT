package emptio.serialization;

import emptio.domain.CredentialsException;
import emptio.domain.CredentialsRepository;
import emptio.domain.user.UserCredentials;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCredentialsRepository implements CredentialsRepository {

    Map<String, UserCredentials> extent = new HashMap<>();

    @Override
    public Optional<UserCredentials> getCredentials(String login) {
        if (extent.containsKey(login))
            return Optional.ofNullable(extent.get(login));
        else
            return Optional.empty();
    }

    @Override
    public Optional<UserCredentials> setCredentials(String login, UserCredentials credentials) {
        if (!extent.containsKey(login))
           return Optional.ofNullable(extent.put(login, credentials));
        else
            return Optional.empty();
    }

    @Override
    public boolean deleteCredentials(String login) {
        extent.remove(login);
        return true;
    }
}
