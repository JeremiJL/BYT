package emptio.domain.cart;


import emptio.domain.product.Product;
import emptio.domain.user.User;
import emptio.serialization.Expirable;
import emptio.serialization.Identifiable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data public class Cart implements Identifiable, Expirable {

    private User owner;
    private List<Product> products;
    private int id;
    private int minutesToLive;
    private LocalDateTime bornDateTime;
}
