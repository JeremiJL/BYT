package emptio.adapters;

import emptio.domain.payment.Payment;
import emptio.domain.payment.PaymentStatus;

public interface PaymentGateway {
    boolean process(Payment payment);
    PaymentStatus getPaymentStatus(int paymentId);
}
