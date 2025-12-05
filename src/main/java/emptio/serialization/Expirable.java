package emptio.serialization;

import java.time.LocalDateTime;

public interface Expirable {

    int getMinutesToLive();
    LocalDateTime getBornDateTime();

    default boolean isDead() {
        return getBornDateTime().plusHours(getMinutesToLive()).isAfter(LocalDateTime.now());
    }
}
