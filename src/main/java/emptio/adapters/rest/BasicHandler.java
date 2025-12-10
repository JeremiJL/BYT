package emptio.adapters.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BasicHandler implements HttpHandler {

    @NonNull private byte[] page;

    public BasicHandler(byte[] page) {
        this.page = page;
    }

    public void showPage(HttpExchange exchange) throws IOException {

        exchange.sendResponseHeaders(200, page.length);
        OutputStream os = exchange.getResponseBody();
        os.write(page);
        os.close();
    }

    public void changePage(Map<String, String> changes) throws IOException {

        String pageAsText = new String(page);

        for (Entry<String, String> change : changes.entrySet()) {
            String templatedKey = "{{ " + change.getKey() + " }}";
            pageAsText = pageAsText.replaceFirst(templatedKey, change.getValue());
        }

    }

}
