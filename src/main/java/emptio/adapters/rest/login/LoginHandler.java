package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;
import emptio.adapters.rest.utils.HttpFormConverter;
import emptio.domain.user.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private void login(String login, String password) {

        int id = UserService.getUserId(login, password);
    }

}
