package emptio.domain.order;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.cart.Cart;
import emptio.domain.payment.Payment;
import emptio.domain.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderService {

    private static Repository<Order> orderRepository;
    private static Set<Validator<Order>> validators;

    public Order newOrder(Cart cart, Campaign campaign) {

        List<Payment> payments = new ArrayList<>();

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


   static private List<Payment> splitCartIntoPayments(Cart cart) {
        List<Payment> list = new ArrayList<>();
        return list;
   }

}
