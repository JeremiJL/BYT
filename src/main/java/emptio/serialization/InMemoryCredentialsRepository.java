package emptio.serialization;

import emptio.domain.CredentialsException;
import emptio.domain.CredentialsRepository;
import emptio.domain.RepositoryException;
import emptio.domain.user.UserCredentials;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCredentialsRepository implements CredentialsRepository {

    Map<String, UserCredentials> extent = new HashMap<>();

    @Override
    public UserCredentials getCredentials(String login) {
        if (extent.containsKey(login))
            return extent.get(login);
        else throw new CredentialsException("No credentials under given login : " + login);
    }

    @Override
    public void setCredentials(String login, UserCredentials credentials) {
        if (!extent.containsKey(login))
            extent.put(login, credentials);
        else
            throw new CredentialsException("Given login is already taken : " + login);
    }
}
