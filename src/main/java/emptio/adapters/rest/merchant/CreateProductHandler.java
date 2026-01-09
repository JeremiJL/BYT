package emptio.adapters.rest.merchant;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.adapters.rest.utils.HttpConverter;
import emptio.adapters.rest.utils.ImageBytesExtractor;
import emptio.common.SymetricEncryptor;
import emptio.domain.RepositoryException;
import emptio.domain.ValidationException;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.common.Currency;
import emptio.domain.product.ProductService;
import emptio.domain.user.Merchant;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class CreateProductHandler extends SessionHandler {

    private final ProductService productService;

    public CreateProductHandler(byte[] pageWithAuthorizedAccess, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor, ProductService productService) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
        this.productService = productService;
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {

        Map<String, String> requestData = HttpConverter.convertFormDataToMap(exchange.getRequestBody().readAllBytes());

        int countOnMarketplace = Integer.parseInt(requestData.get("countOnMarketplace"));
        String title = requestData.get("title");
        String description = requestData.get("description");
        String image = requestData.get("illustration"); // TODO: implement extraction of image data from form post req

        Cost price = new Cost(
                BigDecimal.valueOf(Double.parseDouble(requestData.get("priceValue"))),
                Currency.valueOf(requestData.get("priceCurrency")));
        Category category = Category.valueOf(requestData.get("productCategory"));

        try {
            productService.newProduct(
                    (Merchant) user, price, null, category, title, description, countOnMarketplace
            );
            renderSuccessfulProductCreationPage(exchange);
        } catch (ValidationException | RepositoryException e) {
            renderFailedProductCreationPage(exchange);
        }
    }

    private void renderSuccessfulProductCreationPage(HttpExchange exchange) throws IOException {
        Map<String, String> templateData = Map.of(
                "PRODUCT_CREATION_RESULT", "succeeded.",
                "TRY_AGAIN_REDIRECT_VISIBILITY", "hidden",
                "ADD_NEXT_PRODUCT_REDIRECT_VISIBILITY", "visible"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), templateData));
    }

    private void renderFailedProductCreationPage(HttpExchange exchange) throws IOException {
        Map<String, String> templateData = Map.of(
                "PRODUCT_CREATION_RESULT", "failed, try again.",
                "TRY_AGAIN_REDIRECT_VISIBILITY", "visible",
                "ADD_NEXT_PRODUCT_REDIRECT_VISIBILITY", "hidden"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), templateData));
    }
}
