package emptio.adapters.rest.merchant;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.common.SymetricEncryptor;
import emptio.domain.product.Product;
import emptio.domain.user.Merchant;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class ProductInventoryHandler extends SessionHandler {

    public ProductInventoryHandler(byte[] pageWithAuthorizedAccess, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {
        Set<Product> products = ((Merchant) user).getProducts();
        Map<String,String> templateData = Map.of(
                "PRODUCTS_DEFINITIONS", convertToTemplate(products)
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), templateData));
    }

    private String convertToTemplate(Set<Product> products) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : products) {
            stringBuilder.append("{" + "imgSrc: \"");
            stringBuilder.append(product.getTheImageSource());
            stringBuilder.append("\",");
            stringBuilder.append("title: \"");
            stringBuilder.append(product.getTitle());
            stringBuilder.append("\",");
            stringBuilder.append("priceValue: ");
            stringBuilder.append(product.getPrice().getValue());
            stringBuilder.append(",");
            stringBuilder.append("priceCurrency: \"");
            stringBuilder.append(product.getPrice().getCurrency());
            stringBuilder.append("\"");
            stringBuilder.append("},");
        }
        return stringBuilder.toString();
    }
}
