package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import emptio.common.Enviorment;
import emptio.domain.RepositoryException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class IdService {

    private final String idCollectionPath;

    public IdService(Enviorment enviorment) {
        String idCollectionDirectory = "operations_data/" + enviorment.name() + "/id_collection/";
        this.idCollectionPath = idCollectionDirectory + "id";
        this.initialize(idCollectionDirectory);
    }

    private void initialize(String directory) {
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            throw new RepositoryException("Failed to initialize disk id repository :" + e.getMessage());
        }
    }

    public int getNewId() {
        try {
            File file = new File(this.idCollectionPath);
            ObjectMapper objectMapper = new ObjectMapper();

            if (!file.exists()) {
                objectMapper.writeValue(file, 0);
                return 0;
            } else {
                int latestId = objectMapper.readValue(file, Integer.class);
                int newId = latestId + 1;
                objectMapper.writeValue(file, newId);
                return newId;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
