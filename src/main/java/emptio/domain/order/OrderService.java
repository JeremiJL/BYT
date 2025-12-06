package emptio.domain.order;

import emptio.adapters.PaymentGateway;
import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.cart.Cart;
import emptio.domain.common.Cost;
import emptio.domain.payment.Payment;
import emptio.domain.payment.PaymentService;
import emptio.domain.payment.PaymentStatus;
import emptio.domain.product.Product;
import emptio.domain.user.User;
import emptio.domain.user.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class OrderService {

    private static Repository<Order> orderRepository;
    private static Set<Validator<Order>> validators;
    private static PaymentGateway paymentGateway;

    static public Order newOrder(Cart cart, Campaign campaign) {

        Set<Payment> payments = new HashSet<>();

        if (cart != null)
            payments = splitCartIntoPayments(cart);
        else if (campaign != null)
            payments.add(new Payment(
                    Payment.idService.getNewId(),
                    campaign.getTotalBudget(),
                    UserService.getEmptioUser(),
                    campaign.getOwner()));

        Order order = new Order(payments, LocalDateTime.now());

        return orderRepository.find(
                orderRepository.add(
                        validate(order)
                )
        );
    }

    static private Order validate(Order orderToValidate) {
        try {
            validators.forEach(validator -> validator.validate(orderToValidate));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a campaign with given parameters, cause : " + e.getMessage());
        }
        return orderToValidate;
    }

    static public void requestReturns(Order order) {
        for (Payment payment : order.payments) {
            if (paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE){
                Payment returnPayment = PaymentService.convertToReturnPayment(payment);
                paymentGateway.process(returnPayment);
                order.payments.remove(payment);
                order.returns.add(returnPayment);
            }
            order.payments.remove(payment);
        }
    }

    static public OrderStatus getStatus(Order order) {
        if (order.isDead()) {
            if (order.payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.FAILED;
            else if (order.payments.stream().anyMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE)) {
                requestReturns(order);
                return OrderStatus.FOR_RETURN;
            } else if (order.returns.stream().anyMatch(payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.PROCESSING_RETURN;
            else if (order.returns.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.RETURNED;
            if (order.payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.FAILED;
        } else {
            if (order.payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.COMPLETE;
            else if (order.payments.stream().anyMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.COMPLETE))
                return OrderStatus.HALF_COMPLETE;
            else if (order.payments.stream().allMatch( payment -> paymentGateway.getPaymentStatus(payment.getId()) == PaymentStatus.INCOMPLETE))
                return OrderStatus.INCOMPLETE;
        }
        throw new IllegalStateException("Order Status - reached an impossible state : payments : " + order.payments + " | returns : " + order.returns + " | isDead : " + order.isDead());
    }


   static private Set<Payment> splitCartIntoPayments(Cart cart) {
        Map<User, List<Product>> userProducts = cart.getProducts().stream().collect(groupingBy(Product::getSeller, toList()));
        return userProducts.entrySet().stream().map(entry ->
               new Payment(Payment.idService.getNewId(),
                       Cost.add(entry.getValue().stream().map(Product::getPrice).toArray(Cost[]::new)),
                       entry.getKey(),
                       cart.getOwner()
                       )).collect(Collectors.toSet());
   }

}
