package emptio.domain.order;

import emptio.adapters.PaymentGateway;
import emptio.domain.campaign.Campaign;
import emptio.domain.cart.Cart;
import emptio.domain.payment.PaymentStatus;
import emptio.domain.payment.Payment;
import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data public class Order implements Identifiable, Expirable {

    static PaymentGateway paymentGateway;

    @NonNull List<Payment> payments;
    List<Payment> returns;

    public final Cart cart;
    public final Campaign campaign;

    int id;
    int minutesToLive;
    @NonNull LocalDateTime bornDateTime;

    public void requestReturns() {
        for (Payment payment : payments) {
            if (paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE){
                Payment returnPayment = payment.convertMeToReturnPayment();
                paymentGateway.process(returnPayment);
                payments.remove(payment);
                returns.add(returnPayment);
            }
            payments.remove(payment);
        }
    }

    public OrderStatus getStatus() {
        if (isDead()) {
            if (payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.FAILED;
            else if (payments.stream().anyMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE)) {
                requestReturns();
                return OrderStatus.FOR_RETURN;
            } else if (returns.stream().anyMatch(payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
               return OrderStatus.PROCESSING_RETURN;
            else if (returns.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.RETURNED;
            if (payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.FAILED;
        } else {
            if (payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.COMPLETE;
            else if (payments.stream().anyMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.HALF_COMPLETE;
            else if (payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.INCOMPLETE;
        }
        throw new IllegalStateException("Order Status - reached an impossible state : payments : " + payments + " | returns : " + returns + " | isDead : " + isDead());
    }
}
