package emptio.serialization;

import emptio.domain.Repository;
import emptio.domain.RepositoryException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<T extends Identifiable> implements Repository<T> {

    Map<Integer, T> extent = new HashMap<>();

    @Override
    public Integer add(T identifiable) {
        identifiable.setId(idService.getNewId());
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
        int id = identifiable.getId();

        if (this.find(id) != null) {
            this.remove(id);
            return this.add(identifiable);
        } else
            throw new RepositoryException("Entity of given id : " + id + " doesn't exists. Can not update it.");
    }

    private void remove(Integer id) {
        extent.remove(id);
    }

}

