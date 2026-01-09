package emptio.domain;

import emptio.serialization.Identifiable;

import java.util.List;

public interface SearchRepository <T extends Identifiable> {
    List<Integer> search(T i);
    boolean put(T i) throws RepositoryException;
    String getFeature(T i);
}
