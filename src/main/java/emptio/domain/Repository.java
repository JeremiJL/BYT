package emptio.domain;


import emptio.serialization.Identifiable;

public interface Repository<I extends Identifiable> {
    I add(I i);
    I find(Integer id);
}
