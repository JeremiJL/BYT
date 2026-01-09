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

    @Override
    public User withId(int id) {
        return new Shopper(
                id, getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withName(@NonNull String name) {
        return new Shopper(
                getId(), name, getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withSurname(@NonNull String surname) {
        return new Shopper(
                getId(), getName(), surname, getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withEmail(@NonNull String email) {
        return new Shopper(
                getId(), getName(), getSurname(), email, getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withPhoneNumber(@NonNull String phoneNumber) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), phoneNumber,
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withLogin(@NonNull String login) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                login, getPassword(), getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withPassword(@NonNull String password) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), password, getAddress(), getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withAddress(@NonNull Address address) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), address, getLastLogin(),
                getCart()
        );
    }

    @Override
    public User withLastLogin(@NonNull LocalDate lastLogin) {
        return new Shopper(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), lastLogin,
                getCart()
        );
    }
}
