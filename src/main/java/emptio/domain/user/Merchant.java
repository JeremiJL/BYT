package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.product.Product;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@With @Value public class Merchant extends User {

    @NonNull Set<Product> products;

    public Merchant(@JsonProperty("id") int id, @JsonProperty("name") @NonNull String name, @JsonProperty("surname") @NonNull String surname,
                    @JsonProperty("email") @NonNull String email, @JsonProperty("phoneNumber") @NonNull String phoneNumber,
                    @JsonProperty("login") @NonNull String login,
                    @JsonProperty("password") @NonNull String password,
                    @JsonProperty("address") @NonNull Address address,
                    @JsonProperty("lastLogin") @NonNull LocalDate lastLogin,
                    @JsonProperty("products") @NonNull Set<Product> products) {
        super(id, name, surname, email, phoneNumber, login, password, address, lastLogin);
        this.products = products;
    }

    public Merchant withProducts(Set<Product> products) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                products
        );
    }

    @Override
    public User withId(int id) {
        return new Merchant(
                id, getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withName(@NonNull String name) {
        return new Merchant(
                getId(), name, getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withSurname(@NonNull String surname) {
        return new Merchant(
                getId(), getName(), surname, getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withEmail(@NonNull String email) {
        return new Merchant(
                getId(), getName(), getSurname(), email, getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withPhoneNumber(@NonNull String phoneNumber) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), phoneNumber,
                getLogin(), getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withLogin(@NonNull String login) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                login, getPassword(), getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withPassword(@NonNull String password) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), password, getAddress(), getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withAddress(@NonNull Address address) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), address, getLastLogin(),
                getProducts()
        );
    }

    @Override
    public User withLastLogin(@NonNull LocalDate lastLogin) {
        return new Merchant(
                getId(), getName(), getSurname(), getEmail(), getPhoneNumber(),
                getLogin(), getPassword(), getAddress(), lastLogin,
                getProducts()
        );
    }
}
