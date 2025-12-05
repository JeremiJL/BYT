package emptio.domain.order.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.order.Order;

public class PaymentsAssociation implements Validator<Order> {

    @Override
    public void validate(Order entity) throws ValidationException {
        if (entity.getPayments() == null)
            throw new ValidationException("Order needs assigned payments.");
        if (entity.getPayments().isEmpty())
            throw new ValidationException("Order needs assigned payments.");
    }
}
