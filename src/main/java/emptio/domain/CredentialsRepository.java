package emptio.domain;

import emptio.domain.user.UserCredentials;

public interface CredentialsRepository {
    UserCredentials getCredentials(String login);
    void setCredentials(String login, UserCredentials credentials);
}
