package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import emptio.common.SymetricEncryptor;
import emptio.domain.user.User;
import emptio.domain.user.UserService;

import java.io.IOException;

public class HomeHandler extends SessionHandler {

    public HomeHandler(byte[] homePage, byte[] fallbackPage, UserService userService, SymetricEncryptor symmetricEncryptor) {
        super(homePage, fallbackPage, userService, symmetricEncryptor);
    }

    @Override
    public void handleSession(HttpExchange exchange, User user) throws IOException {
        this.showPage(exchange, getPage());
    }

}
