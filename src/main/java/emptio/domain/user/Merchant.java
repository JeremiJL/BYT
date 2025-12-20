package emptio.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.product.Product;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value public class Merchant extends User {

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
}
