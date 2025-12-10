package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
import emptio.domain.CredentialsException;
import emptio.domain.user.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler extends BasicHandler {

    public LoginHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String,String> requestData = HttpFormConverter.convertToMap(exchange.getRequestBody().readAllBytes());

        String givenLogin = requestData.get("login");
        String givenPassword = requestData.get("password");

        login(givenLogin, givenPassword);

        showPage(exchange);
    }

    private void login(String login, String password) throws IOException {

        Map<String, String> templateData = new HashMap<>();

        try {
            int id = UserService.getUserId(login, password);

            templateData.put("LOGIN_RESULT", "successful");
            templateData.put("HOME_REDIRECT_VISIBILITY", "visible");
            templateData.put("TRY_AGAIN_REDIRECT_VISIBILITY", "hidden");
            templateData.put("USER_ID", String.valueOf(id));

        } catch (Exception e) {

            templateData.put("LOGIN_RESULT", "failed - " + e.getMessage());
            templateData.put("HOME_REDIRECT_VISIBILITY", "hidden");
            templateData.put("TRY_AGAIN_REDIRECT_VISIBILITY", "visible");
            templateData.put("USER_ID", "");
        }

        renderTemplate(templateData);
    }

}
