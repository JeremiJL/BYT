package emptio.domain.cart;


import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;
import lombok.Data;

import java.time.LocalDateTime;

@Data public class Cart implements Identifiable, Expirable {
    private int id;
    private int minutesToLive;
    private LocalDateTime bornDateTime;
}
