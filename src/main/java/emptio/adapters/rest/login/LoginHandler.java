package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpConverter;
import emptio.common.SymetricEncryptor;
import emptio.domain.CredentialsException;
import emptio.domain.RepositoryException;
import emptio.domain.user.UserService;
import lombok.NonNull;

import java.io.IOException;
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

        Map<String,String> requestData = HttpConverter.convertFormDataToMap(exchange.getRequestBody().readAllBytes());

        String login = requestData.get("login");
        String password = requestData.get("password");

        try {
            int userId = userService.getIdOfUser(login, password);
            renderSuccessfulLoginPage(exchange, userId);
        } catch (CredentialsException | RepositoryException e) {
            renderFailedLoginPage(exchange, e.getMessage());
        }
    }

    private void renderSuccessfulLoginPage(HttpExchange exchange, int userId) throws IOException {
        String sessionKey = symmetricEncryptor.encrypt(String.valueOf(userId));
        exchange.getResponseHeaders().add("Set-Cookie", "sessionKey=" + sessionKey + "; Max-Age=3600; Path=/");

        Map<String, String> template = Map.of(
                "LOGIN_RESULT", "successful",
                "HOME_REDIRECT_VISIBILITY", "visible",
                "TRY_AGAIN_REDIRECT_VISIBILITY", "hidden"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

    private void renderFailedLoginPage(HttpExchange exchange, String exceptionMessage) throws IOException {

        Map<String, String> template = Map.of(
                "LOGIN_RESULT", "failed - " + exceptionMessage,
                "HOME_REDIRECT_VISIBILITY", "hidden",
                "TRY_AGAIN_REDIRECT_VISIBILITY", "visible"
        );
        renderPage(exchange, applyDataToTemplate(getDefaultPage(), template));
    }

}
