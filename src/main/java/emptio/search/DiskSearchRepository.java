package emptio.search;

import emptio.common.Enviorment;
import emptio.domain.RepositoryException;
import emptio.domain.SearchRepository;
import emptio.serialization.Identifiable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class DiskSearchRepository <T extends Identifiable> implements SearchRepository <T> {

    private final String collectionPath;

    public DiskSearchRepository(Class<T> clazz, Enviorment enviorment) {
        this.collectionPath = "operations_data/" + enviorment.name() +
                "/search_collection/" + clazz.getName();
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
    public List<Integer> search(String feature) throws RepositoryException {
        List<Integer> ids = new ArrayList<>();
        File file = new File(collectionPath + "/" + feature);
        try {
            Scanner scanner = new Scanner(new FileInputStream(file));
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                ids.add(scanner.nextInt());
            }
            return ids;
        } catch (FileNotFoundException e) {
            throw new RepositoryException(
                    "Failed to read from disk repository, under collection=" + collectionPath + " : " + e.getMessage());
        }
    }

    @Override
    public boolean put(T i) throws RepositoryException {
        int id = i.getId();
        String feature = getFeature(i);
        File file = new File(collectionPath + "/" + feature);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(Integer.toString(id));
            writer.write(",");
            return true;
        } catch (IOException e) {
            throw new RepositoryException(
                    "Failed to save to disk repository, under collection=" + collectionPath + " : " + e.getMessage()
            );
        }
    }
}
