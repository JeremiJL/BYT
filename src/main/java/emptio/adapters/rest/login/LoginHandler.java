package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
import emptio.common.SymetricEncryptor;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler extends BasicHandler {

    @NonNull private final UserService userService;
    @NonNull private final SymetricEncryptor symmetricEncryptor;

    public LoginHandler(byte[] page, UserService userService, SymetricEncryptor symmetricEncryptor) {
        super(page);
        this.userService = userService;
        this.symmetricEncryptor = symmetricEncryptor;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String,String> requestData = HttpFormConverter.convertToMap(exchange.getRequestBody().readAllBytes());

        String login = requestData.get("login");
        String password = requestData.get("password");

        Map<String, String> template = new HashMap<>();

        try {
            int id = userService.getUserId(login, password);
            String sessionKey = symmetricEncryptor.encrypt(String.valueOf(id));

            exchange.getResponseHeaders().add("Set-Cookie", "sessionKey=" + sessionKey + "; Max-Age=3600; Path=/");

            template.put("LOGIN_RESULT", "successful");
            template.put("HOME_REDIRECT_VISIBILITY", "visible");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "hidden");

        } catch (Exception e) {

            template.put("LOGIN_RESULT", "failed - " + e.getMessage());
            template.put("HOME_REDIRECT_VISIBILITY", "hidden");
            template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "visible");
        }

        showPage(exchange, renderTemplate(template));
    }

}
