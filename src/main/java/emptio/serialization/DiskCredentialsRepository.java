package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import emptio.domain.CredentialsException;
import emptio.domain.CredentialsRepository;
import emptio.domain.RepositoryException;
import emptio.domain.user.UserCredentials;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class DiskCredentialsRepository implements CredentialsRepository {

    private final String collectionPath;
    private final ObjectMapper objectMapper;

    public DiskCredentialsRepository() {
        this.collectionPath = "operations_data/credentials_collection/";
        this.objectMapper = new ObjectMapper();
        this.objectMapper.findAndRegisterModules();
        this.initialize();
    }

    private void initialize() {
        try {
            Files.createDirectories(Paths.get(collectionPath));
        } catch (IOException e) {
            throw new RepositoryException("Failed to initialize disk repository :" + e.getMessage());
        }
    }

    @Override
    public Optional<UserCredentials> getCredentials(String login) {
        try {
            File file = new File(getPathToCredentials(login));
            if (!file.exists())
                return Optional.empty();
            return Optional.ofNullable(objectMapper.readValue(file, UserCredentials.class));
        } catch (IOException e) {
            throw new RepositoryException("Failed to read " + login + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public Optional<UserCredentials> setCredentials(String login, UserCredentials credentials) {
        try {
            File file = new File(getPathToCredentials(login));

            if (file.exists())
                return Optional.empty();

            OutputStream outputStream = new FileOutputStream(file);
            String mappedEntity = objectMapper.writeValueAsString(credentials);
            outputStream.write(mappedEntity.getBytes());
            outputStream.close();

            return getCredentials(login);
        } catch (IOException e) {
            throw new RepositoryException("Failed to save " + login + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public boolean deleteCredentials(String login) {
        try {
            Files.deleteIfExists(Paths.get(getPathToCredentials(login)));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private String getPathToCredentials(String login) {
        return collectionPath + "/" + login;
    }
}
