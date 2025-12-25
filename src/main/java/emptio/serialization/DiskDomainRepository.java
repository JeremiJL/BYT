package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import emptio.domain.DomainRepository;
import emptio.domain.RepositoryException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class DiskDomainRepository<T extends Identifiable> implements DomainRepository<T> {

    private final Class<T> clazz;
    private final String collectionPath;
    private final ObjectMapper objectMapper;

    public DiskDomainRepository(Class<T> clazz) {
        this.clazz = clazz;
        this.collectionPath = "operations_data/domain_collection/" + clazz.getName();
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
    public Optional<T> add(T i) throws RepositoryException {
        try {
            File file = new File(collectionPath + "/" + i.getId());
            if (file.exists())
                return Optional.empty();

            OutputStream outputStream = new FileOutputStream(file);
            String mappedEntity = objectMapper.writeValueAsString(i);
            outputStream.write(mappedEntity.getBytes());
            outputStream.close();

            return find(i.getId());
        } catch (IOException e) {
            throw new RepositoryException("Failed to save " + i.getId() + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public Optional<T> find(Integer id) throws RepositoryException {
        try {
            File file = new File(collectionPath + "/" + id);
            if (!file.exists())
                return Optional.empty();

            try (InputStream inputStream = new FileInputStream(file)) {
                T entity = objectMapper.readValue(inputStream, clazz);
                inputStream.close();
                return Optional.of(entity);
            }

        } catch (IOException e) {
            throw new RepositoryException("Failed to read " + id + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public boolean remove(Integer id) throws RepositoryException {
        try {
            File file = new File(collectionPath + "/" + id);
            if (!file.exists())
                return false;
            Files.delete(Paths.get(collectionPath + "/" + id));
            return true;
        } catch (IOException e) {
            throw new RepositoryException("Failed to delete " + id + " due to I/O error : " + e.getMessage());
        }
    }
}
