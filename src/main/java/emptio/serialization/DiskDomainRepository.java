package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import emptio.domain.DomainRepository;
import emptio.domain.RepositoryException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiskDomainRepository<T extends Identifiable> implements DomainRepository<T> {

    private final String collectionPath;
    private final ObjectMapper objectMapper;

    public DiskDomainRepository(String domain) {
        this.collectionPath ="operations_data/domain_collection/" + domain;
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
            OutputStream outputStream = new FileOutputStream(file);

            String mappedEntity = objectMapper.writeValueAsString(i);

            outputStream.write(mappedEntity.getBytes());
            outputStream.close();

        } catch (IOException e) {
            throw new RepositoryException("Failed to save " + i.getId() + " due to I/O error : " + e.getMessage());
        }
        return 0;
    }

    @Override
    public T find(Integer id) {
        return null;
    }

    @Override
    public Integer update(T i) {
        return 0;
    }

    @Override
    public void remove(Integer id) {

    }
}
