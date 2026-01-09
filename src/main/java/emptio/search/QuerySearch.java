package emptio.search;

import emptio.serialization.Identifiable;

import java.util.List;

public interface QuerySearch <T extends Identifiable> {

    public List<T> search(String searchQuery);

}
