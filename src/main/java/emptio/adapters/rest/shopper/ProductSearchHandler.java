package emptio.adapters.rest.shopper;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.adapters.rest.utils.HttpConverter;
import emptio.common.SymetricEncryptor;
import emptio.domain.product.Product;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import emptio.search.ProductQuerySearch;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductSearchHandler extends SessionHandler {

    private final @NonNull ProductQuerySearch productQuerySearch;

    public ProductSearchHandler(byte[] pageWithAuthorizedAccess, UserService userService,
                                @NonNull SymetricEncryptor symmetricEncryptor, ProductQuerySearch productQuerySearch) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
        this.productQuerySearch = productQuerySearch;
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {

        Map<String, String> requestData = HttpConverter.convertFormDataToMap(exchange.getRequestBody().readAllBytes());
        String searchQuery = requestData.get("searchQuery");
        List<Product> matchedProducts = productQuerySearch.search(searchQuery);
        Map<String,String> templateData = Map.of(
                "LISTING_CATEGORY", "You have searched " + searchQuery + ".",
                "PRODUCTS_DEFINITIONS", convertToTemplate(matchedProducts)
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), templateData));
    }

    private String convertToTemplate(List<Product> products) {
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
