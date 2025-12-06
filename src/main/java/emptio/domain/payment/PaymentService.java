package emptio.domain.payment;

import lombok.val;

public class PaymentService {

    static public Payment convertToReturnPayment(Payment payment) {
        val payer = payment.getReceiver();
        val receiver = payment.getPayer();
        return payment.withPayer(payer).withReceiver(receiver);
    }
}
