package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LoginHandler implements HttpHandler {

    private final File homePage = new File("ui/login.html");

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Build response
            FileInputStream fileInputStream = new FileInputStream(homePage);
            byte[] homePageBytes = fileInputStream.readAllBytes();

            // Send output
            exchange.sendResponseHeaders(200, homePageBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(homePageBytes);

            os.close();
        } catch (IOException e) {
            System.out.println("Error in " + this.getClass().getName() +" : " + e);
        }
    }
}
