package emptio.domain;

import emptio.serialization.Identifiable;

public interface DomainRepository<T extends Identifiable> {
    Integer add(T i);
    T find(Integer id);
    default Integer update(T i){
        remove(i.getId());
        return add(i);
    }
    void remove(Integer id);
}

