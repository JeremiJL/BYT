package emptio.adapters.rest.home;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.adapters.rest.utils.HttpConverter;
import emptio.common.SymetricEncryptor;
import emptio.domain.CredentialsException;
import emptio.domain.RepositoryException;
import emptio.domain.ValidationException;
import emptio.domain.user.AccountType;
import emptio.domain.user.Address;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.util.Map;

public class EditProfileHandler extends SessionHandler {

    private final UserService userService;

    public EditProfileHandler(byte[] pageWithAuthorizedAccess, UserService userService, @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
        this.userService = userService;
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {

        Map<String,String> requestData = HttpConverter.convertFormDataToMap(exchange.getRequestBody().readAllBytes());

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
        String emailFormatted = HttpConverter.convertEscapeCharacters(email);

        try {
            userService.updateUser(user, name, surname,
                    emailFormatted, phoneNumber,
                    login, password,
                    new Address(
                            postalCode, streetName, country, city, buildingNumberParsed, apartmentNumberParsed
                    ));
            renderSuccessfulAccountCreationPage(exchange);
        } catch (ValidationException | RepositoryException | CredentialsException e){
            renderFailedAccountCreationPage(exchange, e.getMessage());
        }
    }

    private void renderSuccessfulAccountCreationPage(HttpExchange exchange) throws IOException {
        Map<String, String> template = Map.of(
                "PROFILE_EDIT_RESULT","succeeded",
                "HOME_REDIRECT_VISIBILITY","visible",
                "TRY_AGAIN_REDIRECT_VISIBILITY","hidden"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

    private void renderFailedAccountCreationPage(HttpExchange exchange, String failureReason) throws IOException {
        Map<String, String> template = Map.of(
                "PROFILE_EDIT_RESULT","failed - " + failureReason,
                "HOME_REDIRECT_VISIBILITY","hidden",
                "TRY_AGAIN_REDIRECT_VISIBILITY","visible"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }
}
