package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import emptio.adapters.rest.BasicHandler;

import java.io.IOException;

public class CreateAccountFormHandler extends BasicHandler {

    public CreateAccountFormHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        showPage(exchange, getPage());
    }
}
