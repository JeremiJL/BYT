package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
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

        String login = requestData.get("login");
        String password = requestData.get("password");

        login(login, password);

        showPage(exchange);
    }

    private void login(String login, String password) throws IOException {

        Map<String, String> template = new HashMap<>();

        try {
            int id = UserService.getUserId(login, password);

            template.put("LOGIN_RESULT", "successful");
            template.put("HOME_REDIRECT_VISIBILITY", "visible");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "hidden");
            template.put("USER_ID", String.valueOf(id));

        } catch (Exception e) {

            template.put("LOGIN_RESULT", "failed - " + e.getMessage());
            template.put("HOME_REDIRECT_VISIBILITY", "hidden");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "visible");
            template.put("USER_ID", "");
        }

        renderTemplate(template);
    }

}
