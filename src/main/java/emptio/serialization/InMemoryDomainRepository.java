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

        if (this.find(id) == null)
            extent.put(id, identifiable);
        else
            throw new RepositoryException("Entity of given id : " + id + " already exists. Can not add it.");
        return id;
    }

    @Override
    public T find(Integer id) {
        return extent.get(id);
    }

    @Override
    public Integer update(T identifiable) {

        System.out.println("Size of repository : " + this.extent.size());

        int id = identifiable.getId();

        if (this.find(id) != null) {
            this.remove(id);
            extent.put(id, identifiable);
            return id;
        } else
            throw new RepositoryException("Entity of given id : " + id + " doesn't exists. Can not update it.");
    }

    public void remove(Integer id) {
        extent.remove(id);
    }

}

