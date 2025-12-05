package emptio.domain.order.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.order.Order;

public class AssociationValidator implements Validator<Order> {

    @Override
    public void validate(Order entity) throws ValidationException {
        if (entity.cart == null || entity.campaign == null)
            throw new ValidationException("Order has to refer to cart or to campaign");
        if (entity.cart != null || entity.campaign != null)
            throw new ValidationException("Order can not refer both to cart and campaign.");
    }
}
