package emptio.serialization;

import emptio.domain.DomainRepository;
import emptio.domain.RepositoryException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDomainRepository<T extends Identifiable> implements DomainRepository<T> {

    Map<Integer, T> extent = new HashMap<>();

    @Override
    public Integer add(T identifiable) {
        int id = identifiable.getId();

        if (!this.extent.containsKey(id))
            extent.put(id, identifiable);
        else
            throw new RepositoryException("Entity of given id : " + id + " already exists. Can not add it.");
        return id;
    }

    @Override
    public T find(Integer id) {
        if (!extent.containsKey(id))
            throw new RepositoryException("Entity of given id : " + id + " doesn't exist yet.");
        return extent.get(id);
    }

    public boolean remove(Integer id) {
        extent.remove(id);
    }

}

