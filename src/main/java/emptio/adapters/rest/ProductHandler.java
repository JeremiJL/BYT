package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ProductHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inStream = exchange.getRequestBody();
        Scanner scanner = new Scanner(inStream);
        String data = scanner.nextLine();
        System.out.println(data);
    }
}
