package emptio.adapters.rest.home;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.SessionHandler;
import emptio.common.SymetricEncryptor;
import emptio.domain.user.User;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.util.Map;

public class EditProfileFormHandler extends SessionHandler {

    public EditProfileFormHandler(byte[] pageWithAuthorizedAccess, UserService userService,
                                  @NonNull SymetricEncryptor symmetricEncryptor) {
        super(pageWithAuthorizedAccess, userService, symmetricEncryptor);
    }

    @Override
    public void handleExchangeSession(HttpExchange exchange, User user) throws IOException {
        Map<String, String> template = new java.util.HashMap<>(Map.of(
                "CURRENT_NAME", user.getName(),
                "CURRENT_SURNAME", user.getSurname(),
                "CURRENT_EMAIL", user.getEmail(),
                "CURRENT_PHONE_NUMBER", user.getPhoneNumber(),
                "CURRENT_LOGIN", user.getLogin(),
                "CURRENT_PASSWORD", user.getPassword(),
                "CURRENT_POSTAL_CODE", user.getAddress().getPostalCode(),
                "CURRENT_COUNTRY", user.getAddress().getCountry(),
                "CURRENT_BUILDING_NUMBER", String.valueOf(user.getAddress().getBuildingNumber())
        ));
        template.put("CURRENT_CITY", user.getAddress().getCity());
        if (user.getAddress().getStreetName() != null)
            template.put("CURRENT_STREET_NAME", user.getAddress().getStreetName());
        if (user.getAddress().getApartmentNumber() != null)
            template.put("CURRENT_APARTMENT_NUMBER", user.getAddress().getApartmentNumber().toString());

        renderPage(exchange, applyDataToTemplate(getDefaultPage(),template));
    }
}
