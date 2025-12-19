package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import emptio.domain.DomainRepository;
import emptio.domain.RepositoryException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public Integer add(T i) {
        try {
            File file = new File(collectionPath + "/" + i.getId());
            if (file.exists())
                throw new RepositoryException("Entity of given id : " + i.getId() + " already exists. Can not add it.");

            OutputStream outputStream = new FileOutputStream(file);
            String mappedEntity = objectMapper.writeValueAsString(i);
            outputStream.write(mappedEntity.getBytes());
            outputStream.close();

            return i.getId();
        } catch (IOException e) {
            throw new RepositoryException("Failed to save " + i.getId() + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public T find(Integer id) {
        try {
            File file = new File(collectionPath + "/" + id);
            if (!file.exists())
                throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            throw new RepositoryException("Failed to read " + id + " due to I/O error : " + e.getMessage());
        }
    }

    @Override
    public void remove(Integer id) {
        try {
            File file = new File(collectionPath + "/" + id);
            if (!file.exists())
                throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
            Files.delete(Paths.get(collectionPath + "/" + id));
        } catch (IOException e) {
            throw new RepositoryException("Failed to delete " + id + " due to I/O error : " + e.getMessage());
        }
    }
}
