package emptio.domain;

import emptio.domain.user.UserCredentials;

public interface CredentialsRepository {
    UserCredentials getCredentials(String login) throws CredentialsException, RepositoryException;
    void setCredentials(String login, UserCredentials credentials) throws CredentialsException, RepositoryException;
    void deleteCredentials(String login) throws CredentialsException, RepositoryException;
}
