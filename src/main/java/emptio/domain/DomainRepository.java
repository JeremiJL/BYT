package emptio.domain;

import emptio.serialization.Identifiable;

public interface DomainRepository<T extends Identifiable> {
    Integer add(T i) throws RepositoryException;
    T find(Integer id) throws RepositoryException;
    default Integer update(T i){
        remove(i.getId());
        return add(i);
    }
    boolean remove(Integer id) throws RepositoryException;
}

