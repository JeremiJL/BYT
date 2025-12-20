package emptio.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Arrays;

public class IdService {

    private final String idCollectionPath = "operations_data/id_collection/id";

    public int getNewId() {
        try {
            File file = new File(idCollectionPath);
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
