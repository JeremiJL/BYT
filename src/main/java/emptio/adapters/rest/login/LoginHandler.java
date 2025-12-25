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
import java.util.Optional;

public class LoginHandler extends BasicHandler {

    @NonNull private final UserService userService;
    @NonNull private final SymetricEncryptor symmetricEncryptor;

    public LoginHandler(byte[] page, UserService userService, SymetricEncryptor symmetricEncryptor) {
        super(page);
        this.userService = userService;
        this.symmetricEncryptor = symmetricEncryptor;
    }

    @Override
    public void handleExchange(HttpExchange exchange) throws IOException {

        Map<String,String> requestData = HttpFormConverter.convertToMap(exchange.getRequestBody().readAllBytes());

        String login = requestData.get("login");
        String password = requestData.get("password");

        Optional<Integer> userId = userService.getUserId(login, password);

        if (userId.isPresent()) {
            renderSuccessfulLoginPage(exchange, userId.get());
        } else {
            renderFailedLoginPage(exchange);
        }
    }

    private void renderSuccessfulLoginPage(HttpExchange exchange, int userId) throws IOException {
        String sessionKey = symmetricEncryptor.encrypt(String.valueOf(userId));
        exchange.getResponseHeaders().add("Set-Cookie", "sessionKey=" + sessionKey + "; Max-Age=3600; Path=/");

        Map<String, String> template = new HashMap<>();
        template.put("LOGIN_RESULT", "successful");
        template.put("HOME_REDIRECT_VISIBILITY", "visible");
        template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "hidden");

        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

    private void renderFailedLoginPage(HttpExchange exchange) throws IOException {
        Map<String, String> template = new HashMap<>();
        template.put("LOGIN_RESULT", "failed");
        template.put("HOME_REDIRECT_VISIBILITY", "hidden");
        template.put("TRY_AGAIN_REDIRECT_VISIBILITY", "visible");
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

}
