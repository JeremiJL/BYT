package emptio.serialization;

import emptio.domain.Repository;

public class DiskRepository<T extends Identifiable> implements Repository<T> {

    @Override
    public Integer add(T i) {
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
