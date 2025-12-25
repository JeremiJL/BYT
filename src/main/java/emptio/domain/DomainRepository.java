package emptio.domain;

import emptio.serialization.Identifiable;

import java.util.Optional;

public interface DomainRepository<T extends Identifiable> {
    Optional<T> add(T i) throws RepositoryException;
    Optional<T> find(Integer id) throws RepositoryException;
    default Optional<T> update(T i) {
        try {
            remove(i.getId());
        } catch (RepositoryException e) {
            return Optional.empty();
        }
        return add(i);
    }
    boolean remove(Integer id) throws RepositoryException;
}

