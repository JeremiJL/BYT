package emptio.domain.cart;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.order.Order;
import emptio.domain.product.Product;
import emptio.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class CartService {

    private static Repository<Cart> cartRepository;
    private static List<Validator<Cart>> validators;

    public static Cart newCart(
            User owner, List<Product> products) {

        Cart cart = new Cart(
                owner, products, Cart.idService.getNewId(), 30, LocalDateTime.now()
        );

       return cartRepository.find(
                cartRepository.add(validate(cart))
        );
    }

    static private Cart validate(Cart cartToValidate) {
        try {
            validators.forEach(validator -> validator.validate(cartToValidate));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a cart with given parameters, cause : " + e.getMessage());
        }
        return cartToValidate;
    }

}
