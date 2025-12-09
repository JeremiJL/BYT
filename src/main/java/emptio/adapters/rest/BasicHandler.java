package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BasicHandler implements HttpHandler {

    private final File page;

    public BasicHandler(File page) {
        this.page = page;
    }

    public void showPage(HttpExchange exchange) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(page);
        byte[] pageBytes = fileInputStream.readAllBytes();

        exchange.sendResponseHeaders(200, pageBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(pageBytes);
        os.close();
    }
}
