package emptio.domain.payment;

import emptio.domain.common.Cost;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value public class Payment implements Identifiable {
    int id;
    @NonNull Cost cost;
    @NonNull @With User receiver;
    @NonNull @With User payer;
}
