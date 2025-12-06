package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        System.out.println(exchange.getRequestMethod());
    }
}
