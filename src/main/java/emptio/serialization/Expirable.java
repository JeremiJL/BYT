package emptio.serialization;

public interface Expirable {

    void setMinutesToLive(int minutes);
    int getMinutesToLive();

    void setBornDateTime(int minutes);
    int getBornDateTime();
}
