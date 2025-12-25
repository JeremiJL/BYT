package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
import emptio.domain.RepositoryException;
import emptio.domain.ValidationException;
import emptio.domain.user.AccountType;
import emptio.domain.user.Address;
import emptio.domain.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountHandler extends BasicHandler {

    private final UserService userService;

    public CreateAccountHandler(byte[] page, UserService userService) {
        super(page);
        this.userService = userService;
    }

    @Override
    public void handleExchange(HttpExchange exchange) throws IOException {

        Map<String,String> requestData = HttpFormConverter.convertToMap(exchange.getRequestBody().readAllBytes());

        String accountType = requestData.get("accountType");
        String name = requestData.get("name");
        String surname = requestData.get("surname");
        String email = requestData.get("email");
        String phoneNumber = requestData.get("phoneNumber");
        String login = requestData.get("login");
        String password = requestData.get("password");
        String postalCode = requestData.get("postalCode");
        String streetName = requestData.get("streetName");
        String country = requestData.get("country");
        String city = requestData.get("city");
        String buildingNumber = requestData.get("buildingNumber");
        String apartmentNumber = requestData.get("apartmentNumber");
        
        Integer apartmentNumberParsed = (apartmentNumber == null) ? null : Integer.parseInt(apartmentNumber);
        int buildingNumberParsed = Integer.parseInt(buildingNumber);
        String emailFormatted = formatEscapeCharacters(email);
        AccountType accountTypeFormatted = AccountType.valueOf(accountType);

        try {
            userService.newUser(accountTypeFormatted, name, surname, 
                    emailFormatted, phoneNumber,
                    login, password,
                    new Address(
                            postalCode, streetName, country, city, buildingNumberParsed, apartmentNumberParsed
                    ));

            renderSuccessfulAccountCreationPage(exchange);
        } catch (ValidationException | RepositoryException e){
            renderFailedAccountCreationPage(exchange, e.getMessage());
        }
    }
    
    private void renderSuccessfulAccountCreationPage(HttpExchange exchange) throws IOException {
        Map<String, String> template = new HashMap<>();
        template.put("ACCOUNT_CREATION_RESULT","succeeded");
        template.put("LOGIN_REDIRECT_VISIBILITY","visible");
        template.put("TRY_AGAIN_REDIRECT_VISIBILITY","hidden");
        
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }
    
    private void renderFailedAccountCreationPage(HttpExchange exchange, String failureReason) throws IOException {
        Map<String, String> template = new HashMap<>();
        template.put("ACCOUNT_CREATION_RESULT","failed - " + failureReason);
        template.put("LOGIN_REDIRECT_VISIBILITY","hidden");
        template.put("TRY_AGAIN_REDIRECT_VISIBILITY","visible");

        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

    private String formatEscapeCharacters(String value) {
        return value.replaceFirst("%40", "@");
    }
}
