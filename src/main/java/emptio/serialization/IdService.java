package emptio.serialization;

// Singleton
public class IdService {
    private int value;

    public IdService() {
        this.value = 0;
    }

    public int getNewId() {
        return value++;
    }
}
