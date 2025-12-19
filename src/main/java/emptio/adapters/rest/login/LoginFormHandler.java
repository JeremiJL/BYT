package emptio.adapters.rest.login;

import emptio.adapters.rest.BasicHandler;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class LoginFormHandler extends BasicHandler {

    public LoginFormHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        showPage(exchange, getPage());
    }
}
