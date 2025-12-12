package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;

public class HomeHandler extends BasicHandler {

    public HomeHandler(byte[] page) {
        super(page);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        this.showPage(exchange);
    }
}
