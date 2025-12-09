package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Scanner;

public class HomeHandler implements HttpHandler {

    private final File homePage = new File("ui/home.html");

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Fetch input
            InputStream is = exchange.getRequestBody();
            Scanner scanner = new Scanner(is);

            // Build response
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine())
                response.append(scanner.nextLine());
            is.close();

            // Send output
            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println("Error in HomeHandler : " + e);
        }
    }
}
