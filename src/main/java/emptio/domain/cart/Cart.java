package emptio.domain.cart;


import emptio.domain.product.Product;
import emptio.domain.user.Shopper;
import emptio.domain.user.User;
import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class Cart implements Identifiable, Expirable {
    Shopper owner;
    List<Product> products;
    int id;
    int minutesToLive;
    LocalDateTime bornDateTime;
}
