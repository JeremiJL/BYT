package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;

import java.io.File;
import java.io.IOException;

public class LoginHandler extends BasicHandler {

    public LoginHandler(File page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        showPage(exchange);
    }
}
