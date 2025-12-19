package emptio.domain.user;

import emptio.domain.product.Product;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Value public class Merchant extends User {

    @NonNull Set<Product> products;

    public Merchant(int id, String name, String surname,
                       String email, String number,
                       String login, String password,
                       LocalDate lastLogin, Address address) {
        super(id, name, surname, email, number, login, password, address, lastLogin);
        this.products = Collections.emptySet();
    }

    public Merchant(int id, @NonNull String name, @NonNull String surname, @NonNull String email,
                    @NonNull String phoneNumber, @NonNull String login, @NonNull String password,
                    @NonNull Address address, @NonNull LocalDate lastLogin, @NonNull Set<Product> products) {
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
