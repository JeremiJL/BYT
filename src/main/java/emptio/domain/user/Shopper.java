package emptio.domain.user;

import emptio.domain.cart.Cart;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Value public class Shopper extends User {

    // Nullable
    Cart cart;

    public Shopper(int id, String name, String surname,
                   String email, String number,
                   String login, String password,
                   LocalDate lastLogin, Address address, @NonNull Cart cart) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
        this.cart = null;
    }

    public Shopper(int id, @NonNull String name, @NonNull String surname, @NonNull String email,
                   @NonNull String phoneNumber, @NonNull String login, @NonNull String password,
                   @NonNull Address address, @NonNull LocalDate lastLogin, Cart cart) {
        super(id, name, surname, email, phoneNumber, login, password, address, lastLogin);
        this.cart = cart;
    }

    public Shopper withCart(Cart cart) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                cart
        );
    }
}
