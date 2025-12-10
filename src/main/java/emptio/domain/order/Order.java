package emptio.domain.order;

import emptio.domain.payment.Payment;
import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@Data public class Order implements Identifiable, Expirable {
    @NonNull Set<Payment> payments;
    Set<Payment> returns;
    @NonNull final int id;
    @NonNull final int minutesToLive;
    @NonNull final LocalDateTime bornDateTime;
}
