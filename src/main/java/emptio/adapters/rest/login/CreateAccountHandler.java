package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
import emptio.domain.ValidationException;
import emptio.domain.user.Address;
import emptio.domain.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountHandler extends BasicHandler {

    public CreateAccountHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String,String> requestData = HttpFormConverter.convertToMap(exchange.getRequestBody().readAllBytes());

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


        createNewAccount(
            name,surname,email,phoneNumber,login,password,postalCode,streetName,country,city,buildingNumber,apartmentNumber
        );

        showPage(exchange);
    }

    private void createNewAccount(String name, String surname,
                                  String email, String number,
                                  String login, String password,
                                  String postalCode, String streetName,
                                  String country, String city,
                                  String buildingNumber, String apartmentNumber) throws IOException {

        Map<String, String> template = new HashMap<>();

        try {

            Integer apartmentNumberParsed = (apartmentNumber == null) ? null : Integer.parseInt(apartmentNumber);
            int buildingNumberParsed = Integer.parseInt(buildingNumber);

            UserService.newUser(name,surname,email,number,login,password,
                    new Address(postalCode,streetName,country,city,buildingNumberParsed, apartmentNumberParsed));

            template.put("ACCOUNT_CREATION_RESULT","succeeded");
            template.put("LOGIN_REDIRECT_VISIBILITY","visible");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY","hidden");

        } catch (ValidationException | NumberFormatException e){

            template.put("ACCOUNT_CREATION_RESULT","failed - " + e.getMessage());
            template.put("LOGIN_REDIRECT_VISIBILITY","hidden");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY","visible");
        }

        renderTemplate(template);
    }
}
