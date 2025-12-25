package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.cart.Cart;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@With @Value public class Shopper extends User {

    // Nullable
    Cart cart;

    @JsonCreator
    public Shopper(@JsonProperty("id") int id, @JsonProperty("name") @NonNull String name, @JsonProperty("surname") @NonNull String surname,
                   @JsonProperty("email") @NonNull String email, @JsonProperty("phoneNumber") @NonNull String phoneNumber,
                   @JsonProperty("login") @NonNull String login,
                   @JsonProperty("password") @NonNull String password,
                   @JsonProperty("address") @NonNull Address address,
                   @JsonProperty("lastLogin") @NonNull LocalDate lastLogin,
                   @JsonProperty("cart") Cart cart) {
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
