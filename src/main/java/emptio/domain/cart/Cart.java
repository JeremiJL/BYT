package emptio.domain.cart;


import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;

import java.time.LocalDateTime;

public class Cart implements Identifiable, Expirable {

    private int minutesToLive;
    private LocalDateTime bornDateTime;




    @Override
    public void setMinutesToLive(int minutes) {

    }

    @Override
    public int getMinutesToLive() {
        return 0;
    }

    @Override
    public void setBornDateTime(int minutes) {

    }

    @Override
    public int getBornDateTime() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
