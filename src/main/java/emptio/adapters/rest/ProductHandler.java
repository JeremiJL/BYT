package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ProductHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Fetch input
            InputStream is = exchange.getRequestBody();
            Scanner scanner = new Scanner(is);

            // Build response
            StringBuilder response = new StringBuilder();
            response.append("Endpoint - Product | My response : ");
            while (scanner.hasNextLine())
                response.append(scanner.nextLine());
            is.close();

            // Send output
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
