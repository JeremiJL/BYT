package emptio.domain.payment;

import emptio.domain.common.Cost;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.Value;

@Value public class Payment implements Identifiable {
    int id;
    Cost cost;
    User receiver;
    User payer;

    public Payment convertMeToReturnPayment() {
        return new Payment(id, cost, payer, receiver);
    }
}
