package emptio.adapters.rest.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import emptio.adapters.rest.BasicHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoginFormHandler extends BasicHandler {

    public LoginFormHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        showPage(exchange);
    }
}
