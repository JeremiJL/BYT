package emptio.serialization;

import java.time.LocalDateTime;

public interface Expirable {

    void setMinutesToLive(int minutes);
    int getMinutesToLive();

    void setBornDateTime(LocalDateTime bornDateTime);
    LocalDateTime getBornDateTime();

    default boolean isDead() {
        return getBornDateTime().plusHours(getMinutesToLive()).isAfter(LocalDateTime.now());
    }
}
