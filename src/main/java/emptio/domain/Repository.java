package emptio.domain;

public interface Repository<E> {
    E add(E e);
    E update(E e);
    E find(E e);
}
