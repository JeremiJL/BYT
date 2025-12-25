package emptio.domain;

import emptio.domain.user.UserCredentials;

import java.util.Optional;

public interface CredentialsRepository {
    Optional<UserCredentials> getCredentials(String login) throws CredentialsException, RepositoryException;
    Optional<UserCredentials> setCredentials(String login, UserCredentials credentials) throws CredentialsException, RepositoryException;
    boolean deleteCredentials(String login) throws CredentialsException, RepositoryException;
}
