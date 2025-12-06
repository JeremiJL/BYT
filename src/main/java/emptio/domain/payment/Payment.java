package emptio.domain.payment;

import emptio.domain.common.Cost;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.Value;
import lombok.With;

@Value public class Payment implements Identifiable {
    int id;
    Cost cost;
    @With User receiver;
    @With User payer;
}
