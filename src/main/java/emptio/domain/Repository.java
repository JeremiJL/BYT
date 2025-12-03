package emptio.domain;

import emptio.serialization.IdService;
import emptio.serialization.Identifiable;

public interface Repository<T extends Identifiable> {

    IdService idService = new IdService();

    Integer add(T i);
    T find(Integer id);
    Integer update(T i);
    void remove(Integer id);
}

